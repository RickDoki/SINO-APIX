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
package com.sinosdx.common.gateway.plugin.filter.custom;


import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.configuration.APIXGatewayProperties;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.enums.FilterResultCodeEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.SignAuthGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import com.sinosdx.common.toolkit.auth.SignUtil;
import com.sinosdx.common.toolkit.common.LogUtil;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 数据防篡改签名（认证）
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class SignAuthGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public static final String SUCCESS = "success";
    public static final int CODE = 100006;

    public SignAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Autowired
    private APIXGatewayProperties APIXGatewayProperties;

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        String sign = req.getHeaders().getFirst(AuthConstant.AUTH_SIGN);
        String timestamp = req.getHeaders().getFirst(AuthConstant.TIMESTAMP);
        if (StringUtils.isAnyBlank(sign, timestamp)) {
            return HttpUtil.errorResponse(exchange, FilterResultCodeEnum.SIGN_EMPTY);
        }
        if (StringUtils.isBlank(c.appKey)) {
            return HttpUtil.errorResponse(exchange, FilterResultCodeEnum.SIGN_KEY_EMPTY);
        }
        String method = req.getMethodValue();
        String contentType = req.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        //判断是否为POST请求
        if (HttpMethod.POST.name().equals(method)) {
            if (!MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                return HttpUtil.error401(exchange, ResultCodeEnum.SIGN_CONTENT_TYPE_ERROR);
            }
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        String bodyString = new String(bytes, StandardCharsets.UTF_8);
                        HashMap<String, String> hashMap = JSON.parseObject(bodyString, HashMap.class);
                        Mono<Void> result = verifySign(exchange, hashMap, sign, timestamp, c);
                        if (!result.equals(Mono.empty())) {
                            return result;
                        }
                        exchange.getAttributes().put("POST_BODY", bodyString);
                        DataBufferUtils.release(dataBuffer);

                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse()
                                    .bufferFactory()
                                    .wrap(bytes);
                            return Mono.just(buffer);
                        });
                        //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
        } else if (HttpMethod.GET.name().equals(method)) {
            Map<String, List<String>> requestQueryParams = req.getQueryParams();
            HashMap<String, String> hashMap = new HashMap<>(requestQueryParams.size() + 1);
            for (Object o : requestQueryParams.keySet()) {
                hashMap.put(o.toString(), requestQueryParams.get(o).get(0));
            }
            Mono<Void> result = verifySign(exchange, hashMap, sign, timestamp, c);
            if (!result.equals(Mono.empty())) {
                return result;
            }
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    /**
     * 校验签名
     *
     * @param exchange
     * @param hashMap
     * @param sign
     * @param timestamp
     * @param c
     */
    private Mono<Void> verifySign(ServerWebExchange exchange, HashMap<String, String> hashMap, String sign,
            String timestamp, Config c) {
        hashMap.put(AuthConstant.AUTH_SIGN, sign);
        if (APIXGatewayProperties.isHelp()) {
            String time = String.valueOf(System.currentTimeMillis());
            ((Map<String, String>) hashMap).put("timestamp", time);
            log.debug("help, SignAuth check paramMap:{}", JSON.toJSONString(hashMap));
            log.debug("help, SignAuth time:{},sign:{}", time, SignUtil.sign(hashMap, sign));
        }
        hashMap.put(AuthConstant.TIMESTAMP, timestamp);
        String result = SignUtil.verify(hashMap, c.getAppKey());
        LogUtil.debug(log, "sign auth,param：{},key：{},result：{}", hashMap, c.getAppKey(), result);
        if (SUCCESS.equals(result)) {
            return Mono.empty();
        }
        return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, R.fail(CODE, result));
    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_SIGN.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        /**
         * key
         */
        private String appKey;
        /**
         * 签名位置
         */
        private String signPosition;

    }

}
