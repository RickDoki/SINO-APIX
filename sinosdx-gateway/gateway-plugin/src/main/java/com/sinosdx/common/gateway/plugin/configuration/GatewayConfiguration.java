package com.sinosdx.common.gateway.plugin.configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 网关配置
 *
 * @author pengjiahu
 * @date 2021-06-09 21:42
 * @description
 */
@Slf4j
@Configuration
public class GatewayConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(
            ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }


    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("mt-exec-pool-%d").build();

    @Bean
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), NAMED_THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());

    }
}
