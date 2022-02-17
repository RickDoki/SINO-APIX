/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.common.gateway.plugin.filter.global;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.constants.AppConstant;
import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGlobalFilter;
import com.sinosdx.common.gateway.plugin.service.ApplicationServiceFeign;
import com.sinosdx.common.gateway.plugin.service.AuthenticationServiceFeign;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.utils.ReactiveAddrUtil;
import com.sinosdx.common.toolkit.common.StringUtil;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExecutorService executorService;

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

        // 通过uri获取调用的service code
        String code = StringUtil.splitToList(path, "/").get(0);
        String serviceCode = code;
        String sysClientId = "0";
        try {
            Future<R<JSONObject>> future = executorService.submit(() ->
                    SpringContextHolder.getBean(ApplicationServiceFeign.class).queryAppCodeBySubscribeCode(code));
            R<JSONObject> result = future.get();
            log.info("result:{}", result);
            // 外部接口才会查到结果
            if (result.isSuccess() && null != result.getData()) {
                serviceCode = result.getData().getString("appSubscribedCode");
                sysClientId = result.getData().getInteger("subscribeClientId").toString();

                // 处理外部接口时，将鉴权过滤器链写入缓存

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.mutate()
                .header(HeaderConstant.REQUEST_NO_HEADER_NAME, traceId)
                .header(HeaderConstant.IP, requestIp)
                .header(HeaderConstant.START_TIME_KEY, startTime)
                .header(HeaderConstant.FEIGN_TOKEN_HEADER, "todo:service room certification")
                .header(HeaderConstant.ENV, env)
                .header(HeaderConstant.THREAD, Thread.currentThread().getName())
                .header(GatewayConstants.PATH, path)
                .header(GatewayConstants.SERVICE_CODE, serviceCode)
                .header(GatewayConstants.SYS_CLIENT_ID, sysClientId)
                .build();
        if (log.isDebugEnabled()) {
            log.debug("BaseGlobalFilter headers:{}", JSON.toJSONString(req.getHeaders()));
        }
        return chain.filter(exchange.mutate().request(req).build());
    }
}
