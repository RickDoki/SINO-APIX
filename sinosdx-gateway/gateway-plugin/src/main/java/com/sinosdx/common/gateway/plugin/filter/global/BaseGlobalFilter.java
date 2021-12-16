package com.sinosdx.common.gateway.plugin.filter.global;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.constants.AppConstant;
import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class BaseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(log.isDebugEnabled()){
            log.debug("Enter BaseGlobalFilter");
        }
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getHost();
        final String traceId = UUID.randomUUID().toString();
        final String requestIp = ReactiveAddrUtil.getRemoteAddr(request);
        String startTime = String
                .valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        String env = uri.contains("sandbox") ? AppConstant.SAND_BOX : AppConstant.PRO_CODE;
        request.mutate()
                .header(HeaderConstant.REQUEST_NO_HEADER_NAME, traceId)
                .header(HeaderConstant.IP, requestIp)
                .header(HeaderConstant.START_TIME_KEY, startTime)
                .header(HeaderConstant.FEIGN_TOKEN_HEADER, "todo:service room certification")
                .header(HeaderConstant.ENV, env)
                .header(HeaderConstant.THREAD, Thread.currentThread().getName())
                .build();
        if(log.isDebugEnabled()){
            log.debug("BaseGlobalFilter request.mutate:{}", JSON.toJSONString(request.getHeaders()));
        }
        return chain.filter(exchange.mutate().request(request).build());
    }
}
