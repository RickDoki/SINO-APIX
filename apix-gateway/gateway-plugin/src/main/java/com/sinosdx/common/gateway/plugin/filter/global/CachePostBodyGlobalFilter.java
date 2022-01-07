package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * 缓存请求body的数据
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class CachePostBodyGlobalFilter extends BaseGlobalFilter {

    @Override
    public int getOrder() {
        return FilterOrderEnum.G_CACHE_POST_BODY.getOrder();
    }

    @Override
    public Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        if (null == mediaType) {
            return chain.filter(exchange);
        }
        if (HttpMethod.POST.name().equalsIgnoreCase(serverHttpRequest.getMethodValue())
                && !mediaType.includes(MediaType.MULTIPART_FORM_DATA)) {
            ServerRequest serverRequest = ServerRequest
                    .create(exchange, HandlerStrategies.withDefaults().messageReaders());
            Mono<String> bodyToMono = serverRequest.bodyToMono(String.class);
            return bodyToMono.flatMap(body -> {
                exchange.getAttributes().put(GatewayConstants.CACHED_REQUEST_BODY_STR, body);
                ServerHttpRequest newRequest = new ServerHttpRequestDecorator(serverHttpRequest) {
                    @Override
                    public HttpHeaders getHeaders() {
                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.putAll(super.getHeaders());
                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                        return httpHeaders;
                    }

                    @Override
                    public Flux<DataBuffer> getBody() {
                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(
                                new UnpooledByteBufAllocator(false));
                        DataBuffer bodyDataBuffer = nettyDataBufferFactory.wrap(body.getBytes());
                        return Flux.just(bodyDataBuffer);
                    }
                };
                return chain.filter(exchange.mutate().request(newRequest).build());
            });
        }
        return chain.filter(exchange);
    }
}
