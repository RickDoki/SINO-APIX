package com.sinosdx.common.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2019/11/20 15:13
 * @description
 */
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = GatewayConfig.GATEWAY_PREFIX)
public class GatewayConfig {

    public static final String GATEWAY_PREFIX = "sinosdx.gateway";

    private List<String> ignoredUrl;

    /**
     * 异常请求安全控制
     */
    private ExceptionSafeControl exceptionSafeControl = new ExceptionSafeControl();

    @Getter
    @Setter
    public static class ExceptionSafeControl {

        /**
         * 是否启用
         */
        private boolean enable = true;

    }
}
