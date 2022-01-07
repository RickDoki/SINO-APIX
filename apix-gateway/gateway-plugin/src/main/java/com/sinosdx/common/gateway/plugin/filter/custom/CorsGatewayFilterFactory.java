package com.sinosdx.common.gateway.plugin.filter.custom;


import cn.hutool.core.collection.ListUtil;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.CorsGatewayFilterFactory.Config;
import com.sinosdx.common.toolkit.common.LogUtil;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
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

    public static final String REGEX = "&";

    public CorsGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        if (!CorsUtils.isCorsRequest(req)) {
            LogUtil.debug(log, "[{}] not cors request!", req.getHeaders().getFirst(GatewayConstants.PATH));
            return chain.filter(exchange);
        }
        ServerHttpResponse rep = exchange.getResponse();
        HttpHeaders headers = rep.getHeaders();
        headers.setAccessControlAllowOrigin(c.getAllowOrigins());
        headers.setAccessControlAllowMethods(
                Stream.of(c.getAllowMethods().split(REGEX))
                        .map(HttpMethod::resolve)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
        headers.setAccessControlAllowHeaders(ListUtil.toList(c.getAllowHeaders().split(REGEX)));
        headers.setAccessControlExposeHeaders(ListUtil.toList(c.getExposeHeaders().split(REGEX)));
        headers.setAccessControlMaxAge(c.getMaxAge());
        headers.setAccessControlAllowCredentials(c.allowCredentials);
        exchange.mutate().response(rep).build();
        if (req.getMethod() == HttpMethod.OPTIONS) {
            rep.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        return chain.filter(exchange);

    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_CORS.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        /**
         * 允许的ORIGIN, 用逗号分隔, 默认"*"
         */
        private String allowOrigins = "*";
        /**
         * 允许的方法,GET,POST,PUT,DELETE,HEAD,OPTIONS,PATCH, 默认"*"
         */
        private String allowMethods = "*";
        /**
         * 允许的请求头部,用逗号分隔, 默认"*"
         */
        private String allowHeaders = "*";
        /**
         * 允许暴露给XMLHttpRequest对象的头,用逗号分隔, 默认"*"
         */
        private String exposeHeaders = "*";
        /**
         * 预检请求的有效期，单位为秒, 默认5
         */
        private int maxAge = 5;
        /**
         * 是否允许Cookie, 默认false
         */
        private Boolean allowCredentials = false;

    }

}
