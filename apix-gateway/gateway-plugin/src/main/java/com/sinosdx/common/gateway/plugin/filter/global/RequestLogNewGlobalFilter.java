package com.sinosdx.common.gateway.plugin.filter.global;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import com.sinosdx.common.gateway.plugin.service.IMessageService;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Component
@Slf4j
public class RequestLogNewGlobalFilter extends BaseGlobalFilter {

    private static final String WEBSOCKET = "websocket";
    private static final List<String> STRING_LIST = Arrays.asList("http", "https");

//    @Autowired
//    private IMessageService messageService;

    @Autowired
    private ExecutorService executorService;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;


    @Override
    public int getOrder() {
        return FilterOrderEnum.G_REQUEST_LOG.getOrder();
    }

    @Override
    public Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
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
//        HttpHeaders httpHeaders = exchange.getResponse().getHeaders();
        Integer statusCode = exchange.getResponse().getRawStatusCode() == null ? 0 : exchange.getResponse().getRawStatusCode();
        GatewayLogDTO gatewayLog = new GatewayLogDTO();
//        gatewayLog.setResponseHeaders(LogUtil.getHttpHeaders(httpHeaders));
        gatewayLog.setStatusCode(statusCode);
//        String urlPath = request.getURI().getPath();
//        String traceId = request.getId();
//        String path = stringRedisTemplate.opsForValue().get(traceId);
//        log.info("traceId ==> {},path==>{},==>{}",traceId,path,urlPath);
        SpringContextHolder.getBean(IMessageService.class).saveAnalysisLog(exchange,gatewayLog.getType(),gatewayLog);
        return chain.filter(exchange);
    }
}
