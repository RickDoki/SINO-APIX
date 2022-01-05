package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.service.IMessageService;
import com.sinosdx.common.gateway.utils.GzipUtil;
import com.sinosdx.common.gateway.utils.LogUtil;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
//@Component
@Slf4j
public class RequestLogNewGlobalFilter implements GlobalFilter, Ordered {

    private static final String WEBSOCKET = "websocket";
    private static final List<String> STRING_LIST = Arrays.asList("http", "https");

//    @Autowired
//    private IMessageService messageService;

    @Autowired
    private ExecutorService executorService;

    @Override
    public int getOrder() {
        return FilterOrderEnum.REQUEST_LOG.getOrder();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (log.isDebugEnabled()) {
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
        HttpHeaders httpHeaders = exchange.getResponse().getHeaders();
        Integer statusCode = exchange.getResponse().getRawStatusCode() == null ?0:exchange.getResponse().getRawStatusCode();
        GatewayLogDTO gatewayLog = new GatewayLogDTO();
        gatewayLog.setResponseHeaders(LogUtil.getHttpHeaders(httpHeaders));
        gatewayLog.setStatusCode(statusCode);
        SpringContextHolder.getBean(IMessageService.class).saveAnalysisLog(gatewayLog);
        return chain.filter(exchange);
    }
}
