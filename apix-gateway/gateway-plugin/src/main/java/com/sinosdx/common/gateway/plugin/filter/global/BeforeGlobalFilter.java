package com.sinosdx.common.gateway.plugin.filter.global;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.constants.AppConstant;
import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import com.sinosdx.common.toolkit.common.StringUtil;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 抽象全局自定义过滤器
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class BeforeGlobalFilter extends BaseGlobalFilter {

    @Override
    public int getOrder() {
        return FilterOrderEnum.G_BASE.getOrder();
    }

    @Override
    public Mono<Void> customFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        if (req.getMethod() == HttpMethod.OPTIONS) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        String uri = req.getURI().getHost();
        final String traceId = req.getId();
        final String requestIp = ReactiveAddrUtil.getRemoteAddr(req);
        String startTime = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        String env = uri.contains(AppConstant.SAND_BOX) ? AppConstant.SAND_BOX : AppConstant.PRO_CODE;
        String path = req.getURI().getPath();
        MDC.put(HeaderConstant.REQUEST_NO_HEADER_NAME, traceId);
        req.mutate()
                .header(HeaderConstant.REQUEST_NO_HEADER_NAME, traceId)
                .header(HeaderConstant.IP, requestIp)
                .header(HeaderConstant.START_TIME_KEY, startTime)
                .header(HeaderConstant.FEIGN_TOKEN_HEADER, "todo:service room certification")
                .header(HeaderConstant.ENV, env)
                .header(HeaderConstant.THREAD, Thread.currentThread().getName())
                .header(GatewayConstants.PATH, path)
                .header(GatewayConstants.SERVICE_CODE, StringUtil.splitToList(path).get(0))
                .build();
        if (log.isDebugEnabled()) {
            log.debug("BaseGlobalFilter headers:{}", JSON.toJSONString(req.getHeaders()));
        }
        return chain.filter(exchange.mutate().request(req).build());
    }
}