package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.entity.ResponseInfo;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.service.IMessageService;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 防重放攻击
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class HttpLogGatewayFilterFactory extends BaseGatewayFilter<HttpLogGatewayFilterFactory.Config> {

    private static final String WEBSOCKET = "websocket";
    private static final List<String> STRING_LIST = Arrays.asList("http", "https");
    /**
     * gateway
     */
    String GATEWAY = "gateway";

    public HttpLogGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain,
                                  HttpLogGatewayFilterFactory.Config c) {
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
        Consumer<ResponseInfo> consumer = x -> {
            if (200 == x.getStatusCode()) {
                GatewayLogDTO gatewayLog = new GatewayLogDTO();
                gatewayLog.setParams(getRequestParams(exchange, request));
                gatewayLog.setResponseHeaders(x.getHeaders());
                gatewayLog.setStatusCode(x.getStatusCode());
                gatewayLog.setResult(x.getResult());
                handleResponse(gatewayLog, exchange);
            }
        };
        return chain.filter(exchange.mutate().response(HttpUtil.getResponse(exchange, consumer)).build());
    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_HTTP_LOG.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String ip;

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
        SpringContextHolder.getBean(IMessageService.class).saveLog(exchange,GATEWAY, gatewayLog);
        exchange.getAttributes().remove(GatewayConstants.CACHED_REQUEST_BODY_STR);
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
            //if (BaseConstants.POST.equalsIgnoreCase(request.getMethodValue())) {
            requestParams = exchange
                    .getAttributeOrDefault(GatewayConstants.CACHED_REQUEST_BODY_STR, "");
        } else {
            requestParams = request.getQueryParams().toString();
        }
//        }
        return requestParams;
    }

}
