package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.CorsGatewayFilterFactory.Config;
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
 * Cors跨域
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class CorsGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public CorsGatewayFilterFactory() {
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

        /**
         * 允许的ORIGIN, 用逗号分隔, 默认"*"
         */
        private String allowOrigins;
        /**
         * 允许的方法,GET,POST,PUT,DELETE,HEAD,OPTIONS,PATCH
         */
        private String allowMethods;
        /**
         * 允许的请求头部,用逗号分隔
         */
        private String allowHeaders;
        /**
         * 允许暴露给XMLHttpRequest对象的头,用逗号分隔
         */
        private String exposeHeaders;
        /**
         * 预检请求的有效期，单位为秒
         */
        private String maxAge;
        /**
         * 是否允许Cookie
         */
        private String allowCredentials;

    }

}
