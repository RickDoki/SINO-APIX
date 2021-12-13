package com.sinosdx.service.event.bridge.service.impl.process;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.service.IEventFilterService;
import com.sinosdx.service.event.bridge.service.IEventService;
import com.sinosdx.service.event.bridge.service.IEventTransformService;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.collection.ListUtil;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-09-23 21:27
 * @description
 */
@Slf4j
@Component
public class EventPreProcess {

    @Autowired
    private EventTargetCallProcess eventTargetCallProcess;

    @Autowired
    private IEventTransformService eventTransformService;

    @Autowired
    private IEventFilterService eventFilterService;

    @Autowired
    private IEventService eventService;

    @Async
    public void preProcess(Event event) {
        log.debug("4.事件进行前置处理,{}", event);
        //事件过滤处理,返回命中的规则集合
        List<RuleTarget> matchEventRuleList = eventFilterService.matchEventMode(event);
        if (ListUtil.isEmpty(matchEventRuleList)) {
            log.error("事件[{}]未匹配到规则", event.getEventUuid());
            //修改事件处理状态
            eventService.modifyEventProcessStatus(event.getId());
            return;
        }
        log.debug("5.1.事件[{}]，匹配到规则：{}", event.getEventUuid(),
                JSON.toJSONString(matchEventRuleList));
        //查询规则目标集合
        EventPreProcess eventPreProcess = SpringContextHolder.getBean(EventPreProcess.class);
        matchEventRuleList.forEach(r -> {
            TargetCallBO targetCallBO = new TargetCallBO();
            BeanUtil.copyProperties(r, targetCallBO);
            targetCallBO.setEventId(event.getId());
            targetCallBO.setEventUuid(event.getEventUuid());
            targetCallBO.setRuleTargetId(r.getId());
            targetCallBO.setData(event.getData());
            eventPreProcess.process(targetCallBO, r);
        });
    }

    @Async
    public void process(TargetCallBO targetCallBO, RuleTarget ruleTarget) {
        //事件转换处理
        Object data = eventTransformService.eventTransform(targetCallBO.getData(), ruleTarget);
        targetCallBO.setData(data);
        //事件目标调用
        eventTargetCallProcess.eventTargetCall(targetCallBO);
    }
}
