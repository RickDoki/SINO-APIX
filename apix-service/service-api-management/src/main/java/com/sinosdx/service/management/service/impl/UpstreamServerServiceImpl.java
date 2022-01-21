package com.sinosdx.service.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.common.base.constants.ThreadContextConstant;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.dao.entity.UpstreamServer;
import com.sinosdx.service.management.dao.mapper.UpstreamServerMapper;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.UpstreamServerService;
import com.sinosdx.service.management.utils.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author wendy
 * @date 2021/9/14
 */
@Service
@Slf4j
public class UpstreamServerServiceImpl implements UpstreamServerService {

    @Resource
    private UpstreamServerMapper upstreamServerMapper;

    /**
     * 查询上游服务列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryUpstreamServerList(String name, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }

        Integer userId = ThreadContext.get(ThreadContextConstant.THREAD_CONTEXT_USER_ID);

        List<Object> list = upstreamServerMapper.queryUpstreamServerList(name, userId, limit, offset);
        // 数据集合
        List<Object> upstreamList = (List<Object>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("upstreamList", upstreamList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }

    /**
     * 查询上游服务详情
     *
     * @param id
     * @return
     */
    @Override
    public R<Object> queryUpstreamServerDetail(Integer id) {
        return R.success(upstreamServerMapper.selectById(id));
    }

    /**
     * 新增上游服务
     *
     * @param upstreamServer
     * @return
     */
    @Override
    public R<Object> addUpstreamServer(UpstreamServer upstreamServer) {
        String serverAddress = upstreamServer.getServerAddress();
        if (StringUtils.isAnyEmpty(upstreamServer.getName(),
                upstreamServer.getLoadBalance(),
                serverAddress,
                upstreamServer.getPort(),
                upstreamServer.getProtocol())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        // 判断服务地址合法性
        if (Character.isDigit(serverAddress.charAt(0)) && !Pattern.matches(Constants.REGEX_IP, serverAddress)) {
                return R.fail(ResultCodeEnum.SERVER_ADDRESS_IS_WRONG);
        }

        Long count = upstreamServerMapper.selectCount(new QueryWrapper<UpstreamServer>().eq("name", upstreamServer.getName())
                .eq("server_address", serverAddress)
                .eq("port", upstreamServer.getPort()));
        if (count > 0) {
            return R.fail(ResultCodeEnum.UPSTREAM_SERVER_IS_EXIST);
        }

        upstreamServer.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        upstreamServer.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        upstreamServerMapper.insert(upstreamServer);

        return R.success(upstreamServer);
    }

    /**
     * 修改上游服务
     *
     * @param upstreamServer
     * @return
     */
    @Override
    public R<Object> updateUpstreamServer(UpstreamServer upstreamServer) {
        upstreamServer.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        upstreamServer.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        upstreamServerMapper.updateById(upstreamServer);

        return R.success(upstreamServer);
    }

    /**
     * 删除上游服务
     *
     * @param id
     * @return
     */
    @Override
    public R<Object> deleteUpstreamServer(Integer id) {
        upstreamServerMapper.deleteById(id);
        return R.success();
    }

}
