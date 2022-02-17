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

    void saveAnalysisLog(ServerWebExchange exchange,String logType,GatewayLogDTO gatewayLog);

    void saveLog(ServerWebExchange exchange,String logType,GatewayLogDTO gatewayLog);
}
