package com.sinosdx.common.gateway.plugin.filter.custom;


import cn.hutool.core.util.ObjectUtil;
import com.sinosdx.common.base.constants.BaseConstants;
import com.sinosdx.common.gateway.constants.CacheConstant;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.entity.ResponseData;
import com.sinosdx.common.gateway.plugin.enums.HitStatusEnum;
import com.sinosdx.common.gateway.plugin.event.ResponseDataEvent;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.ProxyCacheGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.plugin.utils.RedisUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 代理缓存
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class ProxyCacheGatewayFilterFactory extends BaseGatewayFilter<Config> {

    private static final String CACHE_STATUS_HEAD = BaseConstants.PRODUCT_NAME + "-Caching-Status";
    private static final String CACHE_KEY = CacheConstant.PROXY_CACHE_DATA_KEY + ":proxyCache:";
    private static final int MAX_EXPIRE = 172800;

    @Autowired
    private RedisUtil redisUtil;

    public ProxyCacheGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        String cacheKey = CACHE_KEY + req.getHeaders().getFirst(GatewayConstants.PATH);
        if (redisUtil.hasKey(cacheKey)) {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add(CACHE_STATUS_HEAD, HitStatusEnum.HIT.name());
            exchange.mutate().response(response).build();
            return HttpUtil.response(exchange, redisUtil.get(cacheKey));
        }
        return chain.filter(exchange);
    }

    @EventListener
    public void listenerLogMessage(ResponseDataEvent responseDataEvent) {
        ResponseData responseData=responseDataEvent.getResponseData();
        ServerWebExchange exchange=responseData.getExchange();
        //TODO 动态从缓存中获取配置
        if(ObjectUtil.isEmpty(responseData.getO()) ){
            return;
        }
        int expire = Math.min(43200, MAX_EXPIRE);
        ServerHttpRequest req = exchange.getRequest();
        String cacheKey = CACHE_KEY + req.getHeaders().getFirst(GatewayConstants.PATH);
        if (!redisUtil.hasKey(cacheKey)) {
            redisUtil.set(cacheKey,responseDataEvent.getResponseData().getO(), (long) expire);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        /**
         * 默认12小时，最长允许的超期时间为48小时（172800秒）, 超过这个时间的配置无效，被视为48小时超期
         */
        private int expire = 43200;

    }

}
