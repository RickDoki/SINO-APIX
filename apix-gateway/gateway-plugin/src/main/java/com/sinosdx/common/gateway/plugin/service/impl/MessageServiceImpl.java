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
        String s = JSON.toJSONString(gatewayLogDTO);
        log.info("aaaaaaaaaaaaa=====> {}",s);
        logService.analysisGatewayLogSave(s);
        //保存审计日志
        if(AUDIT.equals(logType)){
            logService.saveLog(logType,s);
        }
    }
    @Async
    @Override
    public void saveLog(ServerWebExchange exchange,String logType,GatewayLogDTO gatewayLog) {
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        String s = JSON.toJSONString(gatewayLogDTO);
        logService.saveLog(logType,s);
//        SpringContextHolder.getBean(LogServiceFeign.class).saveLog(logType,s);
    }
}
