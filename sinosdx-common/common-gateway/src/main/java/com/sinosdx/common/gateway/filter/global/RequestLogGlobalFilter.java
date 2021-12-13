package com.sinosdx.common.gateway.filter.global;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.gateway.constants.Constants;
import com.sinosdx.common.gateway.service.IMessageService;
import com.sinosdx.common.gateway.utils.LogUtil;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Component
@Slf4j
public class RequestLogGlobalFilter implements GlobalFilter, Ordered {

    private static final String GZIP = "gzip";
    private static final String WEBSOCKET = "websocket";
    private static final List<String> STRING_LIST = Arrays.asList("http", "https");

    @Autowired
    private IMessageService messageService;

    @Override
    public int getOrder() {
        return -97;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            if(log.isDebugEnabled()) {
                log.debug("Enter RequestLogGlobalFilter");
            }
            ServerHttpRequest request = exchange.getRequest();
            URI originalRequestUrl = request.getURI();
            String scheme = originalRequestUrl.getScheme();
            if (!STRING_LIST.contains(scheme)) {
                return chain.filter(exchange);
            }
            String upgrade = request.getHeaders().getUpgrade();
            if (WEBSOCKET.equalsIgnoreCase(upgrade)) {
                return chain.filter(exchange);
            }
            GatewayLogDTO gatewayLog = new GatewayLogDTO();
            gatewayLog.setParams(getRequestParams(exchange, request));
            return chain.filter(exchange.mutate()
                    .response(getResponseDecorator(exchange, gatewayLog)).build())
                    .onErrorResume(e -> {
                        String result = ExceptionUtil.getMessage(e);
                        if(log.isDebugEnabled()) {
                            log.debug("onErrorResume result:{}", result);
                        }
                        gatewayLog.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                        gatewayLog.setResult(result);
                        gatewayLog.setConsumingTime(getConsumingTime(exchange));
                        messageService.saveLog(exchange, gatewayLog);
                        return Mono.error(e);
                    });
        } catch (Exception e) {
            log.error("请求响应日志打印出现异常", e);
            return chain.filter(exchange);
        }
    }

    /**
     * 计算请求耗时时间
     *
     * @param exchange
     * @return
     */
    private Long getConsumingTime(ServerWebExchange exchange) {
        String xSt = exchange.getRequest().getHeaders().getFirst(HeaderConstant.START_TIME_KEY);
        if (StringUtils.isNotBlank(xSt)) {
            Long startTime = Long.parseLong(xSt);
            Long endTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            return endTime - startTime;
        }
        return -1L;
    }

    /**
     * 获取返回结果
     *
     * @param exchange
     * @param gatewayLog
     * @return
     */
    private ServerHttpResponseDecorator getResponseDecorator(ServerWebExchange exchange,
            GatewayLogDTO gatewayLog) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (Objects.requireNonNull(getStatusCode()).equals(HttpStatus.OK)
//                        && body instanceof Flux) {
                // 获取ContentType，判断是否返回JSON格式数据
//                    String originalResponseContentType = exchange
//                            .getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
//                    if (StringUtils.isNotBlank(originalResponseContentType) && CAN_HANDLE
//                            .contains(originalResponseContentType)) {
                //如果加这个判断，会导致经过这定义过滤器返回Monojust无法判断
                //if (body instanceof Flux) {
                if(log.isDebugEnabled()) {
                    log.debug("body instanceof {}", body);
                }
                Flux<? extends DataBuffer> fluxBody;
                try {
                    fluxBody = Flux.from(DataBufferUtils.join(body));
                } catch (Exception e) {
                    log.error("RequestLogGlobalFilter ResponseDecorator Flux.from error!", e);
                    handleResponse(gatewayLog, exchange);
                    return super.writeWith(body);
                }
                return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                    if(log.isDebugEnabled()) {
                        log.debug("super writeWith request response data");
                    }
                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                    DataBuffer join = dataBufferFactory.join(dataBuffers);
                    byte[] content = new byte[join.readableByteCount()];
                    join.read(content);
                    DataBufferUtils.release(join);
                    try {
                        HttpHeaders httpHeaders = exchange.getResponse().getHeaders();
                        int statusCode = Objects.requireNonNull(getStatusCode()).value();
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
                        gatewayLog.setResponseHeaders(LogUtil.getHttpHeaders(httpHeaders));
                        gatewayLog.setStatusCode(statusCode);
                        gatewayLog.setResult(responseData);
                        //自定义返回结果处理后的长度赋值
                        //if (content != null) {
                        // originalResponse.getHeaders().setContentLength(content.length);
                        //}
                        //originalResponse.getHeaders().set("encrypt", "true");
                    } catch (Exception e) {
                        log.error("RequestLogGlobalFilter ResponseDecorator writeWith error!", e);
                        gatewayLog.setResult(
                                "RequestLogGlobalFilter ResponseDecorator writeWith error");
                    } finally {
                        handleResponse(gatewayLog, exchange);
                    }
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

    /**
     * 处理返回结果
     *
     * @param gatewayLog
     * @param exchange
     */
    private void handleResponse(GatewayLogDTO gatewayLog, ServerWebExchange exchange) {
        String redirectUrl = "";
        try {
            //获取直接请求或者负载均衡处理之后需要请求到下游服务的真实URI
            URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
            if (url != null) {
                redirectUrl = url.toString();
            }
        } catch (Exception e) {
            log.error("handleResponse gatewayRequestUrl error!", e);
        }
        gatewayLog.setRedirectUrl(redirectUrl);
        gatewayLog.setConsumingTime(getConsumingTime(exchange));
        messageService.saveLog(exchange, gatewayLog);
        exchange.getAttributes().remove(Constants.CACHED_REQUEST_BODY_STR);
    }

    /**
     * 获取请求参数（post请求从cache中获取）
     *
     * @param exchange
     * @param request
     * @return
     */
    private String getRequestParams(ServerWebExchange exchange, ServerHttpRequest request) {
        String requestParams;
//        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
//        MediaType mediaType = httpHeaders.getContentType();
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        String method = request.getMethodValue();
//        if (Objects.nonNull(mediaType) && HttpUtil.isUploadFile(mediaType)) {
//            requestParams = "上传文件";
//        } else {
//            if (null == mediaType) {
//                log.debug("getRequestParams Content-Type为空,Headers:{}", httpHeaders.toString());
//                requestParams = "Content-Type为空，请求参数无法记录";
//                return requestParams;
//            }
        if (null != contentType && HttpMethod.POST.name().equalsIgnoreCase(method)
                && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            //if (Constants.POST.equalsIgnoreCase(request.getMethodValue())) {
            requestParams = exchange.getAttributeOrDefault(Constants.CACHED_REQUEST_BODY_STR, "");
        } else {
            requestParams = request.getQueryParams().toString();
        }
//        }
        return requestParams;
    }
}
