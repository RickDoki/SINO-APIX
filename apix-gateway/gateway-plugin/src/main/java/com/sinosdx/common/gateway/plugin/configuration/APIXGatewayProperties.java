package com.sinosdx.common.gateway.plugin.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengjiahu
 * @date 2019/11/20 15:13
 * @description
 */
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = APIXGatewayProperties.GATEWAY_PREFIX)
public class APIXGatewayProperties {

    public static final String GATEWAY_PREFIX = "apix.gateway";

    /**
     * 是否启用帮助模式，有助于问题排查，默认不启用
     */
    private boolean help = false;
}
