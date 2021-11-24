package com.sinosdx.service.event.bridge.service;


import com.sinosdx.service.event.bridge.dao.entity.Event;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:55
 * @description
 */
public interface IEventProcessService {

    /**
     * 单个消息offer到队列
     *
     * @param event
     */
    void offerQueue(Event event);

    /**
     * 消息集合offer到队列
     *
     * @param eventList
     */
    void offerQueue(List<Event> eventList);

    /**
     * 启动
     */
    void processEventQueueRun();
}
