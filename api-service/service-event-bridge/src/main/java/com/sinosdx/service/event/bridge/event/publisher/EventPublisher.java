package com.sinosdx.service.event.bridge.event.publisher;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.event.EventProcessEvent;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 事件发布
 *
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@UtilityClass
public class EventPublisher {

    public void publishEvent(Event event) {
        SpringContextHolder
                .publishEvent(new EventProcessEvent(event.getClass().getSimpleName(), event));
    }

}
