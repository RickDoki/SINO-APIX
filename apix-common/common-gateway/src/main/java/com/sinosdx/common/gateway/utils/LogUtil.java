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
package com.sinosdx.common.gateway.utils;

import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * @author pengjiahu
 * @date 2021-06-26 14:38
 * @description
 */
@UtilityClass
public class LogUtil {

    public static final String USER_AGENT = "User-Agent";
    public static final int END = 9996;
    public static final String STR = "...";

    /**
     * 构建日志
     *
     * @param exchange
     * @param gatewayLog
     * @return
     */
    public static GatewayLogDTO buildLog(ServerWebExchange exchange, GatewayLogDTO gatewayLog,
            String serviceId) {
        ServerHttpRequest req = exchange.getRequest();
        String method = req.getMethodValue().toUpperCase();
        URI requestUri = req.getURI();
        String path = requestUri.getPath();
        String trace = req.getHeaders().getFirst(HeaderConstant.REQUEST_NO_HEADER_NAME);
        String env = req.getHeaders().getFirst(HeaderConstant.ENV);
        String thread = req.getHeaders().getFirst(HeaderConstant.THREAD);
        String userAgent = req.getHeaders().getFirst(USER_AGENT);
        gatewayLog.setRequestPath(path);
        gatewayLog.setHttpMethod(method);
        gatewayLog.setAuthorization(RequestUtil.extractToken(req));
        gatewayLog.setRequestUri(requestUri.toString());
        gatewayLog.setServerIp(ReactiveAddrUtil.getLocalAddr());
        gatewayLog.setRemoteIp(ReactiveAddrUtil.getRemoteAddr(req));
        gatewayLog.setServicePort(requestUri.getPort());
        gatewayLog.setTrace(trace);
        gatewayLog.setDomain(req.getURI().getHost());
        gatewayLog.setRequestHeaders(getHttpHeaders(req.getHeaders()));
        gatewayLog.setUserAgent(userAgent);
        gatewayLog.setEndTime(Instant.now().toEpochMilli());
        gatewayLog.setResponseHeaders(getHttpHeaders(exchange.getResponse().getHeaders()));
        gatewayLog.setServiceId(serviceId);
        gatewayLog.setEnv(env);
        gatewayLog.setActionThread(thread);
        if (StringUtils.isNotBlank(gatewayLog.getResult())) {
            String subResult = StringUtils.substring(gatewayLog.getResult(), 0, END).concat(STR);
            gatewayLog.setResult(subResult);
        }
        return gatewayLog;
    }

    /**
     * 获取httpHeader
     *
     * @param httpHeaders
     * @return
     */
    public static String getHttpHeaders(HttpHeaders httpHeaders) {
        return httpHeaders.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ":" + String.join(";", entry.getValue()))
                .collect(Collectors.joining("|"));
    }
}
