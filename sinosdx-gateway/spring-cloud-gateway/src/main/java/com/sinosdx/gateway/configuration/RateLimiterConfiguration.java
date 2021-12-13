package com.sinosdx.gateway.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 路由限流配置
 *
 * @author pengjiahu
 * @date 2020/4/13 13:37
 * @description
 */
@Configuration
public class RateLimiterConfiguration {

    @Primary
    @Bean(value = "remoteAddrKeyResolver")
    KeyResolver remoteAddrKeyResolver() {
        return exchange ->
                Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                                .getAddress()
                                .getHostAddress());
    }

    @Bean(value = "apiKeyResolver")
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}
