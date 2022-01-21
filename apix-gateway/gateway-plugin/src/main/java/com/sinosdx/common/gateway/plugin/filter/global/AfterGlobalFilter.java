package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author pengjiahu
 * @date 2022-01-03 05:12
 * @description
 */
@Slf4j
@Component
public class AfterGlobalFilter extends BaseGlobalFilter {

    @Override
    public Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.G_AFTER.getOrder();
    }
}
