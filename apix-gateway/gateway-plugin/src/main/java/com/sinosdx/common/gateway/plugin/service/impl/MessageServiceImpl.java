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
package com.sinosdx.common.gateway.plugin.service.impl;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.gateway.plugin.service.IMessageService;
import com.sinosdx.common.gateway.plugin.service.LogServiceFeign;
import com.sinosdx.common.gateway.utils.LogUtil;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import com.sinosdx.common.model.log.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author pengjiahu
 * @date 2021-06-26 14:36
 * @description
 */
@Slf4j
@Service
public class MessageServiceImpl implements IMessageService {

    @Value("${spring.application.name:gateway-unknown}")
    private String serviceId;

//    @Autowired
//    private StreamBridge streamBridge;

    @Autowired
    private LogServiceFeign logService;

    public static final String AUDIT = "audit";

    @Async
    @Override
    public void saveLog(ServerWebExchange exchange, GatewayLogDTO gatewayLog) {
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        log.debug("send gatewayLog:{}", JSON.toJSONString(gatewayLogDTO));
        //streamBridge.send(GatewayConstants.LOG_TOPIC, gatewayLogDTO);
    }
    @Async
    @Override
    public void saveAnalysisLog(ServerWebExchange exchange,String logType,GatewayLogDTO gatewayLog) {
//        SpringContextHolder.getBean(LogServiceFeign.class).analysisGatewayLogSave(JSON.toJSONString(gatewayLog));
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        String s = JSON.toJSONString(gatewayLogDTO.getEntity());
//        log.info("aaaaaaaaaaaaa=====> {}",s);
        logService.analysisGatewayLogSave(s);
    }
    @Async
    @Override
    public void saveLog(ServerWebExchange exchange,String logType,GatewayLogDTO gatewayLog) {
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        String s = JSON.toJSONString(gatewayLogDTO.getEntity());
        logService.saveLog(logType,s);
//        SpringContextHolder.getBean(LogServiceFeign.class).saveLog(logType,s);
    }
}
