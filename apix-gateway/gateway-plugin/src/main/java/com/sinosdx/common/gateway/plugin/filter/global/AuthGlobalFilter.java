package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author wendy
 * @date 2022-01-03 05:12
 * @description
 */
@Slf4j
@Component
public class AuthGlobalFilter extends BaseGlobalFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        log.info("reqId: " + req.getId());
        String token = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);

        // 先判断是否添加鉴权插件
        String appCode = req.getHeaders().getFirst(GatewayConstants.SERVICE_CODE);
        // 只要有鉴权过滤器通过为true，都不通过为false
        boolean gatewayAuth = false;
        Object o = redisTemplate.opsForValue().get(GatewayConstants.REDIS_PREFIX_AUTH + req.getId());
        if (null != o) {
            gatewayAuth = (boolean) o;
        }
        Set<String> pluginNameList = redisTemplate.opsForSet().members(GatewayConstants.REDIS_PREFIX_APP_PLUGIN + appCode);
        if ((pluginNameList.contains("jwt") || pluginNameList.contains("base_auth") || pluginNameList.contains("oauth2"))
                && !gatewayAuth) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_SIGNATURE));
        }

        redisTemplate.delete(GatewayConstants.REDIS_PREFIX_AUTH + req.getId());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.G_AUTHORIZE.getOrder();
    }
}
