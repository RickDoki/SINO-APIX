package com.sinosdx.common.gateway.plugin.configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 网关配置
 *
 * @author pengjiahu
 * @date 2021-06-09 21:42
 * @description
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(APIXGatewayProperties.class)
public class GatewayConfiguration {

    /**
     * 计算公式：CPU 核数 / (1 - 阻塞系数 0.8)
     *
     * @return 线程池核心线程数
     */
    public static Integer corePoolSize() {
        int cpuCoreNum = Runtime.getRuntime().availableProcessors();
        return new BigDecimal(cpuCoreNum).divide(new BigDecimal("0.2")).intValue();
    }

    public static Integer maxPoolSize() {
        return corePoolSize() + (corePoolSize() >> 1);
    }

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

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(
                corePoolSize(), maxPoolSize(), 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(4096),
                NAMED_THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());
    }

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("apix-pool-%d").build();

    @Bean
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), NAMED_THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());

    }
}
