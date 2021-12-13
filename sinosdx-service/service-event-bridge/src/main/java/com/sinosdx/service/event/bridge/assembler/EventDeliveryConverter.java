package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.service.event.bridge.controller.dto.EventDeliveryDTO;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface EventDeliveryConverter {

    //实体中如有object类型，会导致数组越界异常
    //EventDelivery targetCallBOTo(TargetCallBO targetCallBO);

    EventDeliveryDTO eventToDTO(Event event);

    List<EventDeliveryDTO.RuleInfo.EventDeliveryInfo> toInfoList(List<EventDelivery> eventDeliveryList);

    List<EventDeliveryDTO.BaseEventDeliveryInfo> toBaseInfoList(List<EventDelivery> eventDeliveryList);
}
