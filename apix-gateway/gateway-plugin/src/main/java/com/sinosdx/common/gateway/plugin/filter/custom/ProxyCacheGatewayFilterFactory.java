package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.constants.BaseConstants;
import com.sinosdx.common.gateway.constants.CacheConstant;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.ProxyCacheGatewayFilterFactory.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 代理缓存
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class ProxyCacheGatewayFilterFactory extends BaseGatewayFilter<Config> {

    private static final String CACHE_STATUS_HEAD_NAME = BaseConstants.PRODUCT_NAME + "-Cache-status";

    private static final String CACHE_KEY = CacheConstant.PROXY_CACHE_DATA_KEY +"proxyCache";

    public ProxyCacheGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();

        //返回response带标识，是否命中， sinosdx-Cache-Status: HIT    MISS
        return chain.filter(exchange);

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String ip;

    }

}
