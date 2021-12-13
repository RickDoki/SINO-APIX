package com.sinosdx.gateway.filter.custom;



import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.utils.HttpUtil;
import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import com.sinosdx.gateway.enums.ResultEnum;
import com.sinosdx.gateway.filter.custom.BlacklistIpGatewayFilterFactory.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class BlacklistIpGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public BlacklistIpGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        final String requestIp = ReactiveAddrUtil.getRemoteAddr(req);
        log.debug("BlacklistIp requestIp:{}", requestIp);
        boolean exist = Stream.of(c.getIp()).anyMatch(s -> Pattern.matches(s, requestIp));
        if (exist) {
            return HttpUtil.successResponse(exchange, ResultEnum.BLACKLIST_IP, requestIp);
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
