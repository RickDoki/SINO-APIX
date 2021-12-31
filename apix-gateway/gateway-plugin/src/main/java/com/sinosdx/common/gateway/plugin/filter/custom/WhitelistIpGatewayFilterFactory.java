package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.enums.ResultEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.WhitelistIpGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import java.util.regex.Pattern;
import java.util.stream.Stream;
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
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class WhitelistIpGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public WhitelistIpGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        final String requestIp = ReactiveAddrUtil.getRemoteAddr(req);
        boolean exist = Stream.of(c.getIp()).anyMatch(s -> Pattern.matches(s, requestIp));
        if (!exist) {
            return HttpUtil.successResponse(exchange, ResultEnum.WHITELIST_IP, requestIp);
        }
        return chain.filter(exchange);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String ip;

    }

}
