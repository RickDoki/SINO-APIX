package com.sinosdx.common.gateway.plugin.filter.custom;

import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author shenjian
 * @create 2022-01-06 15:37
 * @Description 对返回值重写
 */
@Component
@Slf4j
public class RespRewriteGatewayFilterFactory extends BaseGatewayFilter<RespRewriteGatewayFilterFactory.Config>  {

    private static final String REWRITE_RESPONSE="{\"code\":\"200\",\"success\":\"true\",\"msg\":\"\",\"data\":\"\"}";


    protected RespRewriteGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config config) {
//        if(!config.getEnabled()){
//        }
        ServerHttpResponse response = exchange.getResponse();
        byte[] bits = REWRITE_RESPONSE.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json");
        return response.writeWith(Mono.just(buffer));
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {
        private Boolean enabled;
    }
}
