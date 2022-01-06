package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.constants.BaseConstants;
import com.sinosdx.common.gateway.constants.CacheConstant;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.entity.ResponseInfo;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.enums.HitStatusEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.ProxyCacheGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.plugin.utils.RedisUtil;
import com.sinosdx.common.toolkit.common.LogUtil;
import com.sinosdx.common.toolkit.common.StringUtil;
import java.util.function.Consumer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
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

    /**
     * 添加请求头标识是否命中缓存
     */
    private static final String CACHE_STATUS_HEAD = BaseConstants.PRODUCT_NAME + "-Caching-Status";
    /**
     * 缓存key
     */
    private static final String CACHE_KEY = CacheConstant.PROXY_CACHE_DATA_KEY + ":proxyCache:";
    /**
     * 最长允许的超期时间为48小时（172800秒）, 超过这个时间的配置无效，被视为48小时超期
     */
    private static final int MAX_EXPIRE = 172800;

    @Autowired
    private RedisUtil redisUtil;

    public ProxyCacheGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        HttpHeaders headers = req.getHeaders();
        String cacheKey = CACHE_KEY + headers.getFirst(GatewayConstants.PATH);
        if (redisUtil.hasKey(cacheKey)) {
            LogUtil.debug(log, "cacheKey:{} hit cache", cacheKey);
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add(CACHE_STATUS_HEAD, HitStatusEnum.HIT.name());
            exchange.mutate().response(response).build();
            return HttpUtil.response(exchange, redisUtil.get(cacheKey));
        }
        Consumer<ResponseInfo> consumer = x -> {
            int expire = Math.min(c.getExpire(), MAX_EXPIRE);
            expire = expire <= 0 ? MAX_EXPIRE : expire;
            if (StringUtil.isBlank(x.getResult())) {
                log.warn("path:[{}] ProxyCache result is null ,do not cache", headers);
                return;
            }
            redisUtil.set(cacheKey, x.getResult(), (long) expire);
        };
        return chain.filter(exchange.mutate().response(HttpUtil.getResponse(exchange, consumer)).build());
    }

    /**
     * 注意，顺序要小于-1，须要先于NettyWriteResponseFilter过滤器执行，否则无法进入getResponse
     *
     * @return
     */
    @Override
    public int setOrder() {
        return FilterOrderEnum.C_PROXY_CACHE.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        /**
         * 默认12小时
         */
        private int expire = 43200;

    }

}
