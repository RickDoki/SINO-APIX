package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 缓存请求body的数据
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class CacheDataGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return FilterOrderEnum.CACHE_POST_BODY.getOrder();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange);
    }
}
