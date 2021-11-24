package com.sinosdx.gateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * maxInMemorySize配置，解决请求体过大问题
 *
 * @author pengjiahu
 * @date 2021-06-15 11:01
 * @description
 */
@Configuration
@EnableWebFlux
public class WebFluxWebConfig implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024);
    }
}
