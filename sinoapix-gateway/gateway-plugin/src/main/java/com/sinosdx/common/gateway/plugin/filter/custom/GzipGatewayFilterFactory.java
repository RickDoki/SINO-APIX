package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.GzipGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.utils.GzipUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.reactivestreams.Publisher;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

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

    private static final String GZIP = "gzip";

    public GzipGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        HttpHeaders headers = req.getHeaders();
        List<String> encoding = headers.get(HttpHeaders.CONTENT_ENCODING);
        if (!CollectionUtils.isEmpty(encoding) && encoding.contains(GZIP)) {
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
                        byte[] gzipData = GzipUtil.compress(rootData, Charset.defaultCharset().toString());
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


    /**
     * 获取返回结果
     * 上面不好使时替换使用之前方法
     * @param exchange
     * @return
     */
    private ServerHttpResponseDecorator getResponseDecorator(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                //如果加这个判断，会导致经过这定义过滤器返回Monojust无法判断
                //if (body instanceof Flux) {
                if (log.isDebugEnabled()) {
                    log.debug("body instanceof {}", body);
                }
                Flux<? extends DataBuffer> fluxBody;
                try {
                    fluxBody = Flux.from(DataBufferUtils.join(body));
                } catch (Exception e) {
                    log.error("GzipGatewayFilterFactory ResponseDecorator Flux.from error!", e);
                    return super.writeWith(body);
                }
                return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                    if (log.isDebugEnabled()) {
                        log.debug("super writeWith request response data");
                    }
                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                    DataBuffer join = dataBufferFactory.join(dataBuffers);
                    byte[] content = new byte[join.readableByteCount()];
                    join.read(content);
                    DataBufferUtils.release(join);
                    HttpHeaders httpHeaders = exchange.getResponse().getHeaders();
                    //处理gzip请求（方式一）
                    //String responseData = new String(GzipUtil.unCompress(content),StandardCharsets.UTF_8);
                    //byte[] uppedContent = GzipUtil.compress(responseData, StandardCharsets.UTF_8.name());
                    //处理gzip请求（方式二）
                    String responseData = new String(content, StandardCharsets.UTF_8);
                    List<String> strings = httpHeaders.get(HttpHeaders.CONTENT_ENCODING);
                    if (!CollectionUtils.isEmpty(strings) && strings.contains(GZIP)) {
                        responseData = getGZIPContent(content, responseData);
                    } else {
                        responseData = new String(content, StandardCharsets.UTF_8);
                    }
                    log.debug("getResponseDecorator result:{}", responseData);
                    //自定义返回结果处理后的长度赋值
                    //if (content != null) {
                    // originalResponse.getHeaders().setContentLength(content.length);
                    //}
                    //originalResponse.getHeaders().set("encrypt", "true");
                    return bufferFactory.wrap(content);
                }));
            }

            private String getGZIPContent(byte[] content, String responseData) {
                try (
                        GZIPInputStream gzipInputStream = new GZIPInputStream(
                                new ByteArrayInputStream(content), content.length);
                ) {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(gzipInputStream, writer, StandardCharsets.UTF_8);
                    responseData = writer.toString();
                } catch (IOException e) {
                    log.error("request log response filter gzip IO error", e);
                }
                return responseData;
            }
        };
    }

}
