package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.gateway.enums.ResultEnum;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.GatewayConfig;
import com.sinosdx.common.model.log.constants.LogConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 安全控制全局过滤器
 *
 * @author pengjiahu
 * @date 2021-09-10 10:27
 * @description
 */
@Slf4j
@Component
public class SafeControlGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private GatewayConfig gatewayConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!gatewayConfig.getExceptionSafeControl().isEnable()) {
            return chain.filter(exchange);
        }
        ServerHttpRequest req = exchange.getRequest();
        String ip = req.getHeaders().getFirst(HeaderConstant.IP);
        if(StringUtils.isBlank(ip)){
            log.error("SafeControlGlobalFilter params ip is null");
        }
        String cacheKey = LogConstant.EXCEPTION_LOG_BLACKLIST_CACHE_KEY + ":" + ip;
        String result = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(result)) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultEnum.UN_SAFE_REQUEST));
        } else {
            return chain.filter(exchange);
        }
    }

    /**
     * 顺序放在BaseGlobalFilter之后执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -99;
    }
}
