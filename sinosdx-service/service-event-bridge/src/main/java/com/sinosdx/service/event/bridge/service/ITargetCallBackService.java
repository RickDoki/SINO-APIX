package com.sinosdx.service.event.bridge.service;

import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;

/**
 * 事件目标执行结果回调处理
 *
 * @author pengjiahu
 * @date 2020-12-26 01:34
 * @description
 */
public interface ITargetCallBackService {

    /**
     * 事件目标回调
     *
     * @param eventDelivery
     */
    void callBack(EventDelivery eventDelivery);

}
