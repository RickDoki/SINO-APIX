package com.sinosdx.common.gateway.filter.global;

import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class RequestPrintGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -99;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(log.isDebugEnabled()) {
            log.debug("Enter RequestPrintGlobalFilter");
        }
        ServerHttpRequest req = exchange.getRequest();
        Long startTime = System.currentTimeMillis();
        final String requestIp = ReactiveAddrUtil.getRemoteAddr(req);
        return chain.filter(exchange).then().then(Mono.fromRunnable(() -> {
            Long endTime = System.currentTimeMillis();
            if(log.isDebugEnabled()) {
                log.debug("Request - ClientIP:[{}],path:[{}],time:[{}]",
                        requestIp, req.getURI().getRawPath(), (endTime - startTime) + "ms");
            }
        }));
    }
}
