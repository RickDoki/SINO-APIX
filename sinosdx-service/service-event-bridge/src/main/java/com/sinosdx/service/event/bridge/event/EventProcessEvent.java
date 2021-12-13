package com.sinosdx.service.event.bridge.event;

import com.sinosdx.service.event.bridge.dao.entity.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author pengjiahu
 * @date 2020-10-19 12:57
 * @description
 */
@Getter
public class EventProcessEvent extends ApplicationEvent {

    private Event event;

    public EventProcessEvent(Object source, Event event) {
        super(source);
        this.event = event;
    }

}
