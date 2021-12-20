package com.sinosdx.common.gateway.plugin.utils;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import com.sinosdx.common.gateway.constants.Constants;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author pengjiahu
 * @date 2021-06-18 14:04
 * @description
 */
@Slf4j
public class RequestConverter {

    /**
     * 解析加密的请求参数
     *
     * @param exchange 请求信息
     * @param uri      请求地址
     * @return 解析过的参数
     */
    public static Map<String, String> parseRequest(ServerWebExchange exchange, String uri) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue().toUpperCase();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        String serviceId = route.getUri().getHost();
        Map<String, String> params = new HashMap<>(1);
        //获得请求入参信息
        MultiValueMap<String, String> query = serverHttpRequest.getQueryParams();
        query.forEach((k, v) -> params.put(k, v.get(0)));
        // 获取http method参数
        params.put(Constants.REQUEST_HTTP_METHOD_PARAMETER_NAME, method);
        // 获得请求资源服务ID
        params.put(Constants.REQUEST_SERVICE_ID, serviceId);
        // 获取URL路径
        params.put(Constants.REQUEST_URI_PARAMETER_NAME, uri);
        return params;
    }
}
