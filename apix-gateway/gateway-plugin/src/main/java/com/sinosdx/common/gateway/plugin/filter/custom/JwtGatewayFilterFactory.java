package com.sinosdx.common.gateway.plugin.filter.custom;


import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.JwtGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import com.sinosdx.common.toolkit.auth.JwtUtil;
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

import java.util.Map;
import java.util.Set;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description jwt是鉴权的第一道过滤器
 */
@Slf4j
@Component
public class JwtGatewayFilterFactory extends BaseGatewayFilter<Config> {

    @Autowired
    private RedisTemplate redisTemplate;

    public JwtGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        log.info("reqId: " + req.getId());
        String jwt = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        if (StringUtils.isEmpty(jwt)) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        Object o = redisTemplate.opsForValue().get(GatewayConstants.REDIS_PREFIX_AUTH + req.getId());
        if (null != o && (Boolean) o) {
            log.info("其他鉴权插件通过，直接放行");
            return chain.filter(exchange);
        }

//        // 查询服务是否启用OAuth2插件，如果添加但jwt校验不通过，jwt过滤器让请求通过，在OAuth做二次校验
//        String appCode = req.getHeaders().getFirst(GatewayConstants.SERVICE_CODE);
//        boolean oAuth = false;
//        boolean basicAuth = false;
//        Set<String> pluginNameList = redisTemplate.opsForSet().members(GatewayConstants.REDIS_PREFIX_APP_PLUGIN + appCode);
//        if (null == pluginNameList) {
//            log.error("插件数据有误");
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, R.fail(ResultCodeEnum.INTERFACE_INNER_INVOKE_ERROR));
//        }
//        if (pluginNameList.contains("oauth2")) {
//            oAuth = true;
//        }
//        if (pluginNameList.contains("base_auth")) {
//            basicAuth = true;
//        }

//        // 如果不存在basic-auth过滤器，basic开头直接报错
//        if (!basicAuth && jwt.startsWith(AuthConstant.BASIC_HEADER_PREFIX)) {
//            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
//                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
//        }

        R<Object> result;

        // jwt校验
        try {
            Map<String, Claim> verifyJwt = JwtUtil.verifyJwt(null, jwt);
            if (null == verifyJwt) {
                log.error("jwt解析错误");
                return chain.filter(exchange);
//                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
//                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            }
        } catch (Exception e) {
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
        return FilterOrderEnum.C_JWT.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

}
