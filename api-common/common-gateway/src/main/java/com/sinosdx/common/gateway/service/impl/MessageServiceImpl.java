package com.sinosdx.common.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.gateway.constants.Constants;
import com.sinosdx.common.gateway.service.IMessageService;
import com.sinosdx.common.gateway.utils.LogUtil;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import com.sinosdx.common.model.log.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
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

    @Autowired
    private StreamBridge streamBridge;

    @Async
    @Override
    public void saveLog(ServerWebExchange exchange, GatewayLogDTO gatewayLog) {
        LogEvent gatewayLogDTO = new LogEvent("gatewayLog",
                LogUtil.buildLog(exchange, gatewayLog, serviceId));
        log.debug("send gatewayLog:{}", JSON.toJSONString(gatewayLogDTO));
        streamBridge.send(Constants.LOG_TOPIC, gatewayLogDTO);
    }
}
