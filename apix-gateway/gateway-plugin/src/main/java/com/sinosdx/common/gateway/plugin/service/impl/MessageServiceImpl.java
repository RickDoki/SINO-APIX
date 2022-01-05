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

    @Async
    @Override
    public void saveLog(ServerWebExchange exchange, GatewayLogDTO gatewayLog) {
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        log.debug("send gatewayLog:{}", JSON.toJSONString(gatewayLogDTO));
        //streamBridge.send(GatewayConstants.LOG_TOPIC, gatewayLogDTO);
    }

    @Override
    public void saveAnalysisLog(GatewayLogDTO gatewayLog) {
//        SpringContextHolder.getBean(LogServiceFeign.class).analysisGatewayLogSave(JSON.toJSONString(gatewayLog));
        logService.analysisGatewayLogSave(JSON.toJSONString(gatewayLog));
    }

    @Override
    public void saveLog(String logType,GatewayLogDTO gatewayLog) {
        String s = JSON.toJSONString(gatewayLog);
        logService.saveLog(logType,s);
//        SpringContextHolder.getBean(LogServiceFeign.class).saveLog(logType,s);
    }
}
