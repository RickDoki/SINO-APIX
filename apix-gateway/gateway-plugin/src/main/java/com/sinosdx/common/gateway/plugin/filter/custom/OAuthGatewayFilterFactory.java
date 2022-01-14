package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.OAuthGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.service.AuthenticationServiceFeign;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


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

    @Autowired
    private RedisTemplate redisTemplate;

    public OAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        log.info("reqId: " + req.getId());
        final String requestUri = req.getURI().getPath();
        String token = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        if (StringUtils.isEmpty(token)) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        boolean gatewayAuth = (Boolean) redisTemplate.opsForValue().get(GatewayConstants.REDIS_PREFIX_AUTH + req.getId());
        if (gatewayAuth) {
            log.info("其他鉴权插件通过，直接放行");
            return chain.filter(exchange);
        }

//        // 如果Bearer开头，验证不通过，请求不通过；
//        // 如果Basic开头，存在Basic Auth插件，请求通过
//        String appCode = req.getHeaders().getFirst(GatewayConstants.SERVICE_CODE);
//        boolean basicAuth = false;
//        Set<String> pluginNameList = redisTemplate.opsForSet().members(GatewayConstants.REDIS_PREFIX_APP_PLUGIN + appCode);
//        if (null == pluginNameList) {
//            log.error("插件数据有误");
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, R.fail(ResultCodeEnum.INTERFACE_INNER_INVOKE_ERROR));
//        }
//        if (pluginNameList.contains("base_auth")) {
//            basicAuth = true;
//        }
//        if (basicAuth && token.startsWith(AuthConstant.BASIC_HEADER_PREFIX)) {
//            return chain.filter(exchange);
//        }
//
//        // 如果不存在basic-auth过滤器，basic开头直接报错
//        if (!basicAuth && token.startsWith(AuthConstant.BASIC_HEADER_PREFIX)) {
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
//                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
//        }

        R<Object> result;

        // 验证OAuth token
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
//                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                return chain.filter(exchange);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            log.error("验证token错误", e);
//            result = R.fail(ResultCodeEnum.TOKEN_ERROR);
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            return chain.filter(exchange);
        }

        redisTemplate.opsForValue().set(GatewayConstants.REDIS_PREFIX_AUTH + req.getId(), true);
        return chain.filter(exchange);
    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_OAUTH.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

}
