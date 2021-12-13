package com.sinosdx.service.event.bridge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengjiahu
 * @date 2021-01-08 00:41
 * @description
 */
@Configuration
@EnableConfigurationProperties(EventBridgeProperties.class)
public class EventBridgeConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
