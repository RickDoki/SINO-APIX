package com.sinosdx.common.gateway.plugin.utils;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import com.sinosdx.common.gateway.constants.GatewayConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
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

    private static final String ALL = "*";

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
        params.put(GatewayConstants.REQUEST_HTTP_METHOD_PARAMETER_NAME, method);
        // 获得请求资源服务ID
        params.put(GatewayConstants.REQUEST_SERVICE_ID, serviceId);
        // 获取URL路径
        params.put(GatewayConstants.REQUEST_URI_PARAMETER_NAME, uri);
        return params;
    }

    /**
     * Filter the same headers.
     *
     * @param headers        the response headers
     * @param header         header name
     * @param newHeaderValue the new value for header
     */
    public void filterSameHeader(final HttpHeaders headers, final String header, final String newHeaderValue) {
        if (StringUtils.isBlank(newHeaderValue)) {
            return;
        }
        if (ALL.equals(newHeaderValue.trim())) {
            headers.set(header, ALL);
            return;
        }
        final Set<String> newHeaders = Stream.of(newHeaderValue.split(","))
                .map(String::trim).collect(Collectors.toSet());
        List<String> originHeaders = headers.get(header);
        if (CollectionUtils.isNotEmpty(originHeaders)) {
            if (originHeaders.contains(ALL)) {
                return;
            }
            originHeaders = Stream.of(String.join(",", originHeaders).split(","))
                    .map(String::trim).collect(Collectors.toList());
            newHeaders.addAll(originHeaders);
        }
        headers.set(header, String.join(",", newHeaders));
    }
}
