package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.OAuthGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.service.AuthenticationServiceFeign;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OAuthGatewayFilterFactory extends BaseGatewayFilter<Config> {

    @Autowired
    private AuthenticationServiceFeign authenticationService;

    @Autowired
    private ExecutorService executorService;

    public OAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        final String requestUri = req.getURI().getPath();
        String token = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        if (StringUtils.isEmpty(token)
                || (StringUtils.isNotEmpty(token) && !token
                .startsWith(AuthConstant.AUTH_HEADER_PREFIX))) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        R<Object> result;

        // 验证中台jwt
        if (StringUtils.isNotEmpty(token)) {
            String realToken = token.substring(AuthConstant.AUTH_HEADER_PREFIX.length());
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("accessToken", realToken);
            paramMap.put("uri", requestUri);
            Future<R<Object>> future = executorService
                    .submit(() -> authenticationService.tokenVerify(paramMap));
            try {
                result = future.get();
                log.info("result:{}", result);
                if (!result.isSuccess()) {
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("验证token错误", e);
                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            }
        }

        return chain.filter(exchange);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

}
