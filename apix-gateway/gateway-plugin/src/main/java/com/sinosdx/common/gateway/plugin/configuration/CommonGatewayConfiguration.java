package com.sinosdx.common.gateway.plugin.configuration;

import com.sinosdx.common.gateway.plugin.interception.FeignClientInterception;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengjiahu
 * @date 2021-07-15 14:38
 * @description
 */
@Slf4j
@Configuration
public class CommonGatewayConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor feignClientInterception() {
        log.info("-----------configuration bean feignClientInterception-----------");
        return new FeignClientInterception();
    }
}
