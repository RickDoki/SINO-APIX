package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.service.event.bridge.dao.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class EventDTO extends Event {

}
