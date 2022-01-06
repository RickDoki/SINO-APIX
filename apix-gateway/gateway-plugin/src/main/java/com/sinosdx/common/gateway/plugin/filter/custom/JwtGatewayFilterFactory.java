package com.sinosdx.common.gateway.plugin.filter.custom;


import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.JwtGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import com.sinosdx.common.toolkit.auth.JwtUtil;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
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
public class JwtGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public JwtGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        String jwt = req.getHeaders().getFirst(AuthConstant.AUTH_JWT);
        if (StringUtils.isEmpty(jwt)) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        R<Object> result;

        // 验证csp2.0jwt
        if (StringUtils.isNotEmpty(jwt)) {
            try {
                Map<String, Claim> verifyJwt = JwtUtil.verifyJwt(null, jwt);
                if (null == verifyJwt) {
                    log.error("jwt解析错误");
                    result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("验证token错误", e);
                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_JWT.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

}
