package com.sinosdx.service.event.bridge.mq;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.service.event.bridge.config.EventBridgeProperties;
import com.sinosdx.service.event.bridge.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * MQ消费者业务
 *
 * @author pengjiahu
 * @date 2020-08-29
 * @description Note:
 * 1.消费需要成功，不能抛出异常，否则会导致第一次成功，后续失败
 */
@Slf4j
@Component
public class Consumer {

    @Autowired
    private EventBridgeProperties eventBridgeProperties;

    @Autowired
    private IEventService eventService;

    @Bean
    public Function<Flux<Message<CloudEvent>>, Mono<Void>> eventBridge() {
        return flux -> flux.map(message -> {
            log.debug("接收到mq事件消息：{}", JSON.toJSONString(message));
            eventService.saveEvent(message.getPayload());
            return message;
        }).then();
    }

    @Bean
    public Function<Flux<Message<Object>>, Mono<Void>> errorLog() {
        return flux -> flux.map(message -> {
            String msg = JSON.toJSONString(message.getPayload().toString());
            log.debug("接收错误日志消息：{}", msg);
            CloudEvent cloudEvent = CloudEvent.builder()
                    .busName(eventBridgeProperties.getBusName())
                    .source(eventBridgeProperties.getSource())
                    .type(eventBridgeProperties.getType())
                    .specVersion("1.0")
                    .eventUuid(UUID.randomUUID().toString())
                    .data(msg)
                    .build();
            eventService.saveEvent(cloudEvent);
            return message;
        }).then();
    }

    @Bean
    public Function<Flux<Message<CloudEvent>>, Mono<Void>> dynamicEvent() {
        return flux -> flux.map(message -> {
            log.debug("接收到动态dest消息：{}", JSON.toJSONString(message));
            return message;
        }).then();
    }
}
