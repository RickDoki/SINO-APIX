package com.sinosdx.service.event.bridge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.service.event.bridge.controller.dto.EventDeliveryDTO;
import com.sinosdx.service.event.bridge.controller.query.EventDeliveryQuery;
import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:55
 * @description
 */
public interface IEventDeliveryService {

    /**
     * 查询事件轨迹详情
     *
     * @param eventId
     * @return
     */
    EventDeliveryDTO getEventDeliveryDetail(Integer eventId);

    /**
     * 查询事件轨迹列表
     *
     * @param page
     * @param eventDeliveryQuery
     * @return
     */
    IPage getEventDeliveryList(Page page, EventDeliveryQuery eventDeliveryQuery);

    /**
     * 批量保存
     *
     * @param eventDeliveryList
     * @return
     */
    boolean saveBatchEventDelivery(List<EventDelivery> eventDeliveryList);

    /**
     * 保存
     *
     * @param eventDelivery
     * @return
     */
    boolean saveEventDelivery(EventDelivery eventDelivery);

    /**
     * 入队
     *
     * @param eventDelivery
     */
    void offerQueue(EventDelivery eventDelivery);

    /**
     * 批量入队
     *
     * @param eventList
     */
    void offerQueue(List<EventDelivery> eventList);

    /**
     * 查询执行失败的事件及目标
     *
     * @param page
     * @return
     */
    IPage getEventDeliveryFailList(Page page);

    /**
     * 启动
     */
    void saveEventDeliveryRun();
}
