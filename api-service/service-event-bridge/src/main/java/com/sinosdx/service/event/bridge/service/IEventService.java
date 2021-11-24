package com.sinosdx.service.event.bridge.service;

import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.service.event.bridge.controller.dto.EventDTO;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:55
 * @description
 */
public interface IEventService {

    /**
     * 查询事件详情
     *
     * @param id
     * @return
     */
    EventDTO getEventDetail(Integer id);

    /**
     * 修改事件处理状态
     *
     * @param id
     * @return
     */
    boolean modifyEventProcessStatus(Integer id);

    /**
     * 保存事件
     *
     * @param cloudEvent
     */
    Object saveEvent(CloudEvent cloudEvent);

}
