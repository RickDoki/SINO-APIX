package com.sinosdx.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 跨域动态配置
 *
 * @author pengjiahu
 * @date 2019/11/20 10:49
 * @description
 */
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = CorsProperties.CORS_PREFIX)
public class CorsProperties {

    public static final String CORS_PREFIX = "sinosdx.gateway.cors";

    private Boolean enable = false;

    private Boolean allowCredentials;

    private String allowedOrigin;

    private String allowedHeader;

    private String allowedMethod;

    private String exposedHeader;

    private Long maxAge;
}
