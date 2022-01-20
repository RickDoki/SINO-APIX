package com.sinosdx.common.gateway.plugin.filter.custom;


import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.BasicAuthGatewayFilterFactory.Config;
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

import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class BasicAuthGatewayFilterFactory extends BaseGatewayFilter<Config> {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private RedisTemplate redisTemplate;

    public BasicAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        log.info("reqId: " + req.getId());
        String basicAuth = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        if (StringUtils.isEmpty(basicAuth)) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        Object o = redisTemplate.opsForValue().get(GatewayConstants.REDIS_PREFIX_AUTH + req.getId());
        if (null != o && (Boolean) o) {
            log.info("其他鉴权插件通过，直接放行");
            return chain.filter(exchange);
        }

        R<JSONObject> result;

        if (StringUtils.isNotEmpty(basicAuth)) {
            try {
                String realToken = basicAuth.substring(AuthConstant.BASIC_HEADER_PREFIX.length());
                if (StringUtils.isEmpty(realToken)) {
                    return chain.filter(exchange);
                }
                String usernameAndPwd = new String(Base64.getDecoder().decode(realToken));
                String pwd = usernameAndPwd.split(":")[1];

                Future<R<JSONObject>> future = executorService
                        .submit(() -> SpringContextHolder.getBean(AuthenticationServiceFeign.class).queryClientSecret(pwd));
                result = future.get();
                log.info("result:{}", result);
                if (!result.isSuccess() || null == result.getData()) {
                    return chain.filter(exchange);
                    //                    result = R.fail(ResultCodeEnum.TOKEN_ERROR);
//                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("验证token错误", e);
//                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
//                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                return chain.filter(exchange);
            }
        } else {
            log.error("token为空");
//            result = R.fail(ResultCodeEnum.TOKEN_ERROR);
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            return chain.filter(exchange);

        }

        log.info("basic鉴权通过");
        redisTemplate.opsForValue().set(GatewayConstants.REDIS_PREFIX_AUTH + req.getId(), true);
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
