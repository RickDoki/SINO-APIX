package com.sinosdx.common.gateway.plugin.service;

import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author pengjiahu
 * @date 2021-06-26 14:35
 * @description
 */
public interface IMessageService {

    /**
     * 保存请求请求日志
     *
     * @param exchange
     * @param gatewayLog
     * @return
     */
    void saveLog(ServerWebExchange exchange, GatewayLogDTO gatewayLog);

    void saveAnalysisLog(GatewayLogDTO gatewayLog);

    void saveLog(String logType,GatewayLogDTO gatewayLog);
}
