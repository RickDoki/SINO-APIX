package com.sinosdx.common.gateway.plugin.filter.custom;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.AuthorizeGatewayFilterFactory.Config;
import com.sinosdx.common.gateway.plugin.service.AuthenticationServiceFeign;
import com.sinosdx.common.gateway.plugin.utils.HttpUtil;
import com.sinosdx.common.gateway.properties.AuthConstant;
import com.sinosdx.common.tools.auth.SignUtil;
import com.sinosdx.common.tools.common.JwtUtil;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description
 */
@Slf4j
@Component
public class AuthorizeGatewayFilterFactory extends BaseGatewayFilter<Config> {

    @Autowired
    private AuthenticationServiceFeign authenticationService;

    @Autowired
    private ExecutorService executorService;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        final String requestUri = req.getURI().getPath();
        String token = req.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        String sign = req.getHeaders().getFirst(AuthConstant.AUTH_SIGN);
        String jwt = req.getHeaders().getFirst(AuthConstant.AUTH_JWT);
        if (StringUtils.isAllEmpty(token, sign, jwt)
                || (StringUtils.isNotEmpty(token) && !token.startsWith(AuthConstant.AUTH_HEADER_PREFIX))) {
            return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED,
                    R.fail(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT));
        }

        R<Object> result;

        // 验证中台jwt
        if (StringUtils.isNotEmpty(token)) {
            String realToken = token.substring(AuthConstant.AUTH_HEADER_PREFIX.length());
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("accessToken", realToken);
            paramMap.put("uri", requestUri);
            //TODO 有问题，会导致该过滤器无效
            Future<R<Object>> future = executorService.submit(() -> authenticationService.tokenVerify(paramMap));
            try {
                result = future.get();
                log.info("result:{}", result);
                if (!result.isSuccess()) {
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("验证token错误", e);
                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            }
        }

        // 验证签名
        if (StringUtils.isNotEmpty(sign)) {
            String method = req.getMethodValue();
            String contentType = req.getHeaders().getFirst("Content-Type");
            //判断是否为POST请求
            if ("POST".equals(method)) {
                if (!"application/json".equals(contentType)) {
                    log.error("验签错误，不支持json以外的请求类型");
                    result = R.fail(ResultCodeEnum.SIGN_CONTENT_TYPE_ERROR);
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
                return DataBufferUtils.join(exchange.getRequest().getBody())
                        .flatMap(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            String bodyString = new String(bytes, StandardCharsets.UTF_8);
                            //TODO 得到Post请求的请求参数后，做你想做的事
                            log.info("bodyString====={}", bodyString);
                            HashMap<String, String> hashMap = JSON.parseObject(bodyString, HashMap.class);
                            if (!verifySign(hashMap, sign)) {
                                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, R.fail(ResultCodeEnum.SIGN_ERROR));
                            }
                            exchange.getAttributes().put("POST_BODY", bodyString);
                            DataBufferUtils.release(dataBuffer);

                            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                                DataBuffer buffer = (DataBuffer) exchange.getResponse().bufferFactory()
                                        .wrap(bytes);
                                return Mono.just(buffer);
                            });
                            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
                            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                                    exchange.getRequest()) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return cachedFlux;
                                }
                            };
                            //封装request，传给下一级
                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        });
            } else if ("GET".equals(method)) {
                Map<String, List<String>> requestQueryParams = req.getQueryParams();
                //TODO 得到Get请求的请求参数后，做你想做的事
                HashMap<String, String> hashMap = new HashMap<>();
                for (Object o : requestQueryParams.keySet()) {
                    hashMap.put(o.toString(), requestQueryParams.get(o).get(0));
                }
                if (!verifySign(hashMap, sign)) {
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, R.fail(ResultCodeEnum.SIGN_ERROR));
                }
                return chain.filter(exchange);
            }
        }

        // 验证csp2.0jwt
        if (StringUtils.isNotEmpty(jwt)) {
            try {
                Map<String, Claim> verifyJwt = JwtUtil.verifyJwt(null, jwt);
                if (null == verifyJwt) {
                    log.error("jwt解析错误");
                    result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                    return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("验证token错误", e);
                result = R.fail(ResultCodeEnum.TOKEN_ERROR);
                return HttpUtil.response(exchange, HttpStatus.UNAUTHORIZED, result);
            }
        }
        return chain.filter(exchange);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

        private String flag;

    }

    private boolean verifySign(HashMap<String, String> hashMap, String sign) {
        hashMap.put("sign", sign);
        String result = SignUtil.verify(hashMap, "sinosdx");
        log.info("验签：" + result);
        return "success".equals(result);
    }

}
