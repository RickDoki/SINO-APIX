package com.sinosdx.service.event.bridge.service;

import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;

import java.util.List;

/**
 * 事件过滤
 *
 * @author pengjiahu
 * @date 2020-11-29 10:34
 * @description
 */
public interface IEventFilterService {

    /**
     * 匹配事件规则模式
     *
     * @param event
     * @return
     */
    List<RuleTarget> matchEventMode(Event event);
}
