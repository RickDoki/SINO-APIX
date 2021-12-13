package com.sinosdx.service.event.bridge.event.listener;

import com.sinosdx.service.event.bridge.event.EventProcessEvent;
import com.sinosdx.service.event.bridge.service.IEventProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
@Component
public class EventProcessListener {

    @Autowired
    private IEventProcessService eventProcessService;

    @Async
    @EventListener
    public void processEventListener(EventProcessEvent eventProcessEvent) {
        log.debug("1.EventProcessListener监听到新事件：{}", eventProcessEvent);
        eventProcessService.offerQueue(eventProcessEvent.getEvent());
    }

}
