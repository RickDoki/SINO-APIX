package com.sinosdx.service.event.bridge.constants;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:26
 * @description
 */
public interface CommonConstant {

    /**
     * 投递状态，成功：1，失败：2
     */
    int DELIVERY_STATUS_SUCCESS = 1;
    int DELIVERY_STATUS_FAIL = 2;

    /**
     * 处理状态：1：已处理（默认）2：未匹配到规则
     */
    int PROCESS_STATUS_NO = 2;

    /**
     * 动态事件Topic名称
     */
    String DYNAMIC_EVENT = "dynamic_event";
}
