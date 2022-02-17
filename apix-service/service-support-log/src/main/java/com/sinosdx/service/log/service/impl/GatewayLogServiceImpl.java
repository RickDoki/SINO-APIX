/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.common.base.base.service.SuperServiceImpl;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import com.sinosdx.service.log.dao.mapper.GatewayLogMapper;
import com.sinosdx.service.log.service.IGatewayLogService;
import com.sinosdx.service.log.service.dto.HomeStatisticsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Service
public class GatewayLogServiceImpl extends SuperServiceImpl<GatewayLogMapper, GatewayLog> implements
        IGatewayLogService {

    @Resource
    private GatewayLogMapper gatewayLogMapper;

    /**
     * 查询网关日志
     *
     * @param httpMethod
     * @param domain
     * @param requestPath
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryGatewayLogList(String httpMethod, String domain, String requestPath, Long startTime, Long endTime, String appCode,Integer statusCode, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }
        if (null == startTime) {
            startTime = DateUtils.addWeeks(new Date(), -1).getTime();
        }
        if (null == endTime) {
            endTime = System.currentTimeMillis();
        }
        String responseStatus = "";
        if(Objects.nonNull(statusCode)){
            if(statusCode == 200){
                responseStatus = "ok";
            }else {
                responseStatus = "fail";
            }
        }
        List<Object> list = gatewayLogMapper.queryGatewayLogList(httpMethod, domain, requestPath, startTime, endTime, appCode,responseStatus,limit, offset);
        // 数据集合
        List<GatewayLog> logList = (List<GatewayLog>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("logList", logList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }

    @Override
    public R<Object> queryGatewayByAppCode(List<String> appCodes, Long startDate, Long endDate) {
//        List<String> keys = Arrays.asList("1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00")
        List<String> keys = Lists.newArrayList();
        // 初始化 横坐标 keys
        LocalDateTime startDateTime =LocalDateTime.ofEpochSecond(startDate/1000,0, ZoneOffset.ofHours(8));
        LocalDateTime endDateTime =LocalDateTime.ofEpochSecond(endDate/1000,0, ZoneOffset.ofHours(8));
        // 计算两个时间之间间隔多少小时
        int days = Integer.parseInt(startDateTime.until(endDateTime, ChronoUnit.HOURS)+"");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:00");
        for (int i = 1; i <= days; i++) {
            LocalDateTime localDateTime = startDateTime.plusHours(i);
            String formatTime = dateTimeFormatter.format(localDateTime);
            keys.add(formatTime);
        }
        List<Integer> values = new ArrayList<>(Collections.nCopies(24, 0));
        if (!CollectionUtils.isEmpty(appCodes)) {
            LambdaQueryWrapper<GatewayLog> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(!CollectionUtils.isEmpty(appCodes), GatewayLog::getAppCode, appCodes);
            queryWrapper.between(GatewayLog::getEventTime, startDate, endDate);
            List<GatewayLog> gatewayLogs = this.baseMapper.selectList(queryWrapper);
            //  构造 首页数据统计 数据结构
            gatewayLogs.forEach(e -> {
                long eventTime = e.getEventTime();
                Date eventDate = new Date(eventTime);
                SimpleDateFormat dateFormat = new SimpleDateFormat("H:00");
                String formatDate = dateFormat.format(eventDate);
                e.setGroupKey(formatDate);
            });
            // ①将事件格式化 ② 根据格式化后的数据进行分组 (k为格式化的时间,v 为 List<GatewayLog> 求数量)
            Map<String, List<GatewayLog>> listGroup = gatewayLogs.stream().collect(Collectors.groupingBy(GatewayLog::getGroupKey));
            listGroup.forEach((k, v) -> {
                if (keys.contains(k)) {
                    int index = keys.indexOf(k);
                    values.set(index, v.size());
                }
            });
        }
        HomeStatisticsDTO homeStatisticsDTO = new HomeStatisticsDTO().setKeys(keys).setValues(values);
        return R.success(homeStatisticsDTO);
    }
}
