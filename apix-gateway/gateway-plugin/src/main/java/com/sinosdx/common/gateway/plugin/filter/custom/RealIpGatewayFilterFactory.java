package com.sinosdx.common.gateway.plugin.filter.custom;


import com.sinosdx.common.base.constants.HeaderConstant;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.gateway.entity.BaseConfig;
import com.sinosdx.common.gateway.plugin.enums.FilterOrderEnum;
import com.sinosdx.common.gateway.plugin.filter.BaseGatewayFilter;
import com.sinosdx.common.gateway.plugin.filter.custom.RealIpGatewayFilterFactory.Config;
import java.net.InetSocketAddress;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Real Ip获取真实ip
 *
 * @author pengjiahu
 * @date 2021-06-18 00:43
 * @description 如果网关前置服务是反向代理组件，
 * 如Nginx，需配置
 * proxy_set_header Host $host;
 * proxy_set_header X-Real-IP $remote_addr;
 * proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 */
@Slf4j
@Component
public class RealIpGatewayFilterFactory extends BaseGatewayFilter<Config> {

    public RealIpGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public Mono<Void> customApply(ServerWebExchange exchange, GatewayFilterChain chain, Config c) {
        ServerHttpRequest req = exchange.getRequest();
        if (log.isDebugEnabled()) {
            try {
                XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver
                        .maxTrustedIndex(1);
                InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
                String realIp = inetSocketAddress.getAddress().getHostAddress();
                String baseGlobalIp = req.getHeaders().getFirst(HeaderConstant.IP);
                String addressHostAddress = req.getRemoteAddress().getAddress().getHostAddress();
                String addressHostName = req.getRemoteAddress().getAddress().getHostName();
                String address = req.getRemoteAddress().getAddress().toString();
                String hostName = req.getRemoteAddress().getHostName();
                log.debug("\nrealIp:{}\n"
                                + "baseGlobalIp:{}\n"
                                + "addressHostAddress:{}\n"
                                + "addressHostName:{}\n"
                                + "address:{}\n"
                                + "hostName:{}",
                        realIp, baseGlobalIp, addressHostAddress, addressHostName, address, hostName);
            } catch (Exception e) {
                log.error("RealIpGatewayFilterFactory error", e);
            }
        }
        req.mutate()
                .header(GatewayConstants.REAL_IP, req.getHeaders().getFirst(HeaderConstant.IP))
                .build();
        return chain.filter(exchange.mutate().request(req).build());
    }

    @Override
    public int setOrder() {
        return FilterOrderEnum.C_REAL_IP.getOrder();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Config extends BaseConfig {

    }

}
