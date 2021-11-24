package com.sinosdx.client.event;

import com.sinosdx.common.base.factory.YamlPropertySourceFactory;
import com.sinosdx.client.event.service.impl.SendEventCompletionImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author pengjiahu
 * @date 2020-11-28 20:58
 * @description
 */
@ComponentScan("com.sinosdx.middle.bus.api.event")
@PropertySource(value = "classpath:event-mq.yml", factory = YamlPropertySourceFactory.class)
@Configuration
@Slf4j
public class EventBridgeAutoConfiguration {

    @Bean
    public SendEventCompletionImpl sendEventCompletion(StreamBridge streamBridge){
        return new SendEventCompletionImpl(streamBridge);
    }
}
