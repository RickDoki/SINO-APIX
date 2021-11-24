package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.service.event.bridge.controller.dto.EventDTO;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import org.mapstruct.Mapper;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface EventConverter {

    EventDTO toDTO(Event event);

    Event cloudEventTo(CloudEvent cloudEvent);

}
