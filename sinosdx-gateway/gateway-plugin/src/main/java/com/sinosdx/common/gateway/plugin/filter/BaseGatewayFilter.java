package com.sinosdx.common.gateway.plugin.filter;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.tools.common.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽象自定义过滤器
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Setter
@Getter
public abstract class BaseGatewayFilter<C extends BaseConfig> extends
        AbstractGatewayFilterFactory<C> {

    private Class<C> configClass;

    protected BaseGatewayFilter(Class<C> configClass) {
        super(configClass);
        this.configClass = configClass;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        List<String> a = Arrays.stream(configClass.getSuperclass().getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
        List<String> b = Arrays.stream(configClass.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
        return a.stream().sequential().collect(Collectors.toCollection(() -> b));
    }

    @Override
    public GatewayFilter apply(C config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().toString().toLowerCase();
            if (!checkAuthVerifyExclude(config, path)) {
                if(log.isDebugEnabled()) {
                    log.debug("config:{}", JSON.toJSONString(config));
                }
                return customApply(exchange, chain, config);
            }
            return chain.filter(exchange);
        });
    }

    /**
     * 自定义处理
     *
     * @param exchange
     * @param chain
     * @param config
     * @return
     */
    public abstract Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain,
            C config);

    /**
     * 自定义过滤排除列表的校验
     *
     * @param config
     * @param path
     * @return
     */
    public boolean checkAuthVerifyExclude(C config, String path) {
        String authExcludeUri = config.getAuthExcludeUri();
        if(log.isDebugEnabled()) {
            log.debug("path:{},authExcludeUri:{}", path, authExcludeUri);
        }
        if (StringUtil.isBlank(authExcludeUri)) {
            return false;
        }
        List<String> list = Arrays.stream(authExcludeUri.split(";")).filter(StrUtil::isNotEmpty)
                .collect(Collectors.toList());
        for (String uri : list) {
            if (path.contains(uri.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
