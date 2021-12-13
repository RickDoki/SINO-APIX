package com.sinosdx.gateway.configuration;

import com.sinosdx.gateway.properties.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

/**
 * 配置跨域
 *
 * @author pengjiahu
 * @date 2019/11/20 10:49
 * @description 优先通过官方配置在配置中定义
 */
@Slf4j
//@Configuration
@ConditionalOnProperty(prefix = "mt.gateway.cors", name = "enable", havingValue = "true")
@Deprecated
public class GatewayCorsConfiguration {

    @Autowired
    private CorsProperties corsProperties;

    @Bean
    @ConditionalOnMissingBean(CorsWebFilter.class)
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeader().split(",")));
        config.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethod().split(",")));
        config.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigin().split(",")));
        config.setExposedHeaders(Arrays.asList(corsProperties.getExposedHeader().split(",")));
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.setMaxAge(corsProperties.getMaxAge());
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        log.info("=============自动配置 corsFilter=============");
        return new CorsWebFilter(source);
    }
}
