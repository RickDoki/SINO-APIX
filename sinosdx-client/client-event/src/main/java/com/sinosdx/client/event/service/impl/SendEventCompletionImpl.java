package com.sinosdx.client.event.service.impl;


import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.client.event.service.IEventCompletion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author pengjiahu
 * @date 2020-04-26
 * @description
 */
@Slf4j
@AllArgsConstructor
public class SendEventCompletionImpl implements IEventCompletion {

    private static final String EVENT_TOPIC = "event_bridge";

    private StreamBridge streamBridge;

    @Override
    public void onCompletion(CloudEvent cloudEvent) {
        try {
            boolean send = streamBridge
                    .send(EVENT_TOPIC, MessageBuilder.withPayload(cloudEvent).build());
            log.debug("event sdk send，result：{}", send);
            if (!send) {
                log.error("event sdk send fail! please check");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("event sdk send fail,Exception:{}", e.getMessage());
        }
    }
}
