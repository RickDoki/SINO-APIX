package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.service.impl.GrayLoadBalancer;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局灰度发布过滤器
 *
 * @author pengjiahu
 * @date 2021-09-07 16:33
 * @description 示例：
 * 路由配置：grayLB-w://hello 、grayLB-v://hello,两种形式，w表示权重,v表示版本
 * 服务配置：在nacos配置元数据即可，
 * metadata:
 * version: v2
 * weight: 9
 */
@Slf4j
@Component
@AllArgsConstructor
public class GrayLoadBalancerClientGlobalFilter implements GlobalFilter, Ordered {

    public static final String GRAY_LB = "grayLB";
    public static final String NULL = "null";
    private final LoadBalancerClientFactory clientFactory;

    @Override
    public int getOrder() {
        //顺序不能低于以下值，否则会获取不到url值
        return FilterOrderEnum.GRAY_LOAD_BALANCER.getOrder();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = StringUtils.defaultString(
                exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR),
                NULL);
        if (url != null && (url.getScheme().contains(GRAY_LB) || schemePrefix.contains(GRAY_LB))) {
            ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
            if (log.isDebugEnabled()) {
                log.debug(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: "
                        + url);
            }
            return this.choose(exchange).doOnNext(response -> {
                if (!response.hasServer()) {
                    throw NotFoundException
                            .create(true, "找不到服务示例： " + url.getHost());
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (!NULL.equals(schemePrefix)) {
                        overrideScheme = url.getScheme();
                    }
                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(
                            response.getServer(), overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);
                    if (log.isDebugEnabled()) {
                        log.debug("LoadBalancerClientFilter url chosen: " + requestUrl);
                    }
                    exchange.getAttributes()
                            .put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                }
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }

    /**
     * 重新构建URI地址
     *
     * @param serviceInstance
     * @param original
     * @return
     */
    private URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String host = uri.getHost();
        return new GrayLoadBalancer(
                clientFactory.getLazyProvider(host, ServiceInstanceListSupplier.class),
                host, uri.getScheme())
                .choose(this.createRequest(exchange));
    }

    /**
     * 取原始请求头构建新的默认请求
     *
     * @param exchange
     * @return
     */
    private Request createRequest(ServerWebExchange exchange) {
        return new DefaultRequest<>(exchange.getRequest().getHeaders());
    }
}
