package com.sinosdx.common.gateway.plugin.filter;

import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.toolkit.common.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author pengjiahu
 * @date 2022-01-07 13:04
 * @description
 */
@Slf4j
public abstract class BaseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return FilterOrderEnum.G_DEFAULT.getOrder();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LogUtil.debug(log, "Enter Global {} {}", super.getClass().getSimpleName(), getOrder());
        return customFilter(exchange, chain);
    }

    /**
     * 自定义全局过滤器逻辑
     *
     * @param exchange
     * @param chain
     * @return
     */
    public abstract Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain);
}
