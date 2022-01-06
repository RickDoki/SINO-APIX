package com.sinosdx.common.gateway.plugin.filter.custom;


import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.AuthorizeGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.service.AuthenticationServiceFeign;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import com.sinosdx.common.toolkit.auth.JwtUtil;
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
public class AuthorizeGatewayFilterFactory extends BaseGatewayFilter<Config> {

    @Autowired
    private ExecutorService executorService;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        final String requestUri = req.getURI().getPath();
        String token = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);

        String jwt = req.getHeaders().getFirst(AuthConstant.AUTH_JWT);
        if (StringUtils.isAllEmpty(token, jwt)
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
            //TODO 有问题，会导致该过滤器无效
            Future<R<Object>> future = executorService
                    .submit(() -> SpringContextHolder.getBean(AuthenticationServiceFeign.class).tokenVerify(paramMap));
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
        return FilterOrderEnum.C_AUTHORIZE.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

}
