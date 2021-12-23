package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.GzipGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.utils.GzipUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * Gzip 压缩
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class GzipGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public GzipGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        HttpHeaders headers = req.getHeaders();
        String encoding = headers.getFirst(HttpHeaders.CONTENT_ENCODING);
        if ("gzip".equals(encoding)) {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            ServerHttpResponseDecorator gzipResponse = new ServerHttpResponseDecorator(originalResponse);
            gzipResponse.writeWith(body -> {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    fluxBody.buffer().map(dataBuffer -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffer);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        //释放掉内存
                        DataBufferUtils.release(join);
                        // 正常返回的数据
                        String rootData = new String(content, Charset.defaultCharset());
                        //压缩后数据返回给客户端
                        byte[] gzipData =  GzipUtil.compress(rootData,Charset.defaultCharset().toString());
                        return bufferFactory.wrap(gzipData);
                    });
                }
            });
            return chain.filter(exchange.mutate().response(gzipResponse).build());
        }
        return chain.filter(exchange);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String ip;

    }

}
