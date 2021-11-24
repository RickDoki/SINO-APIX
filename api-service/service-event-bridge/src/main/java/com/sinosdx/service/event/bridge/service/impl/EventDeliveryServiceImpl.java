package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Queues;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.EventDeliveryConverter;
import com.sinosdx.service.event.bridge.config.EventBridgeProperties;
import com.sinosdx.service.event.bridge.controller.dto.EventDeliveryDTO;
import com.sinosdx.service.event.bridge.controller.query.EventDeliveryQuery;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;
import com.sinosdx.service.event.bridge.dao.entity.Rule;
import com.sinosdx.service.event.bridge.dao.mapper.EventDeliveryMapper;
import com.sinosdx.service.event.bridge.dao.mapper.EventMapper;
import com.sinosdx.service.event.bridge.dao.mapper.RuleMapper;
import com.sinosdx.service.event.bridge.enums.ExceptionEnum;
import com.sinosdx.service.event.bridge.service.IEventDeliveryService;
import com.sinosdx.service.event.bridge.service.ITargetCallBackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sinosdx.service.event.bridge.constants.CommonConstant.*;


/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "event_delivery")
public class EventDeliveryServiceImpl extends
        ServiceImpl<EventDeliveryMapper, EventDelivery> implements IEventDeliveryService {

    private final BlockingQueue<EventDelivery> eventDeliveryBlockingQueue = new LinkedBlockingQueue<>();

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    private EventBridgeProperties eventBridgeProperties;

    @Autowired(required = false)
    private EventDeliveryConverter eventDeliveryConverter;

    @Autowired
    private ITargetCallBackService targetCallBackService;

    @Override
    public void offerQueue(EventDelivery eventDelivery) {
        offerQueue(Collections.singletonList(eventDelivery));
    }

    @Override
    public void offerQueue(List<EventDelivery> eventDeliveryList) {
        try {
            eventDeliveryList.forEach(event -> {
                //需要回调的目标进行处理
                targetCallBackService.callBack(event);
                //写入队列
                boolean result = eventDeliveryBlockingQueue.offer(event);
                if (!result) {
                    log.error("事件轨迹处理，数据写入队列失败");
                }
            });
        } catch (Exception e) {
            log.error("事件轨迹处理，数据写入队列发生错误！", e);
        }
    }

    @Override
    public EventDeliveryDTO getEventDeliveryDetail(Integer eventId) {
        //事件
        Event event = eventMapper.selectById(eventId);
        if (event == null) {
            AssertsUtil.fail(ExceptionEnum.EVENT_NOT_FOUND);
        }
        //事件规则
        List<Rule> ruleList = new LambdaQueryChainWrapper<>(ruleMapper)
                .eq(Rule::getBusId, event.getBusId()).list();
        //事件投递轨迹
        List<EventDelivery> eventDeliveryList = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(EventDelivery::getEventId, eventId).list();
        //构建事件轨迹
        EventDeliveryDTO eventDeliveryDTO = eventDeliveryConverter.eventToDTO(event);
        List<EventDeliveryDTO.RuleInfo> eventRuleInfoList = new ArrayList<>();
        for (Rule rule : ruleList) {
            //规则所对应的投递轨迹
            List<EventDelivery> deliveryList = getDelivery(eventDeliveryList, rule.getId());
            List<EventDeliveryDTO.RuleInfo.EventDeliveryInfo> deliveryInfoList = eventDeliveryConverter
                    .toInfoList(deliveryList);
            deliveryInfoList.parallelStream().forEach(item -> {
                List<EventDelivery> deliveryCallbackList = getDeliveryCallback(eventDeliveryList,
                        item.getCallbackRuleTargetId());
                item.setEventDeliveryCallbackList(
                        eventDeliveryConverter.toBaseInfoList(deliveryCallbackList));
            });
            EventDeliveryDTO.RuleInfo ruleInfo = new EventDeliveryDTO.RuleInfo();
            ruleInfo.setEventName(rule.getName());
            ruleInfo.setRuleId(rule.getId());
            ruleInfo.setSuccessNum(
                    getDeliveryStatusNum(eventDeliveryList, rule.getId(), DELIVERY_STATUS_SUCCESS));
            ruleInfo.setFailNum(
                    getDeliveryStatusNum(eventDeliveryList, rule.getId(), DELIVERY_STATUS_FAIL));
            ruleInfo.setEventDeliveryInfoList(deliveryInfoList);
            eventRuleInfoList.add(ruleInfo);
        }
        eventDeliveryDTO.setRuleInfoList(eventRuleInfoList);
        return eventDeliveryDTO;
    }

    /**
     * 过滤集合中需要的数据
     *
     * @param list
     * @param ruleId
     * @return
     */
    public List<EventDelivery> getDelivery(List<EventDelivery> list, Integer ruleId) {
        return list.parallelStream()
                .filter(e -> e.getRuleId() != null && e.getRuleId().equals(ruleId))
                .collect(Collectors.toList());
    }

    /**
     * 过滤集合中需要的回调数据
     *
     * @param list
     * @param callbackId
     * @return
     */
    public List<EventDelivery> getDeliveryCallback(List<EventDelivery> list, Integer callbackId) {
        return list.parallelStream()
                .filter(e -> e.getRuleId() == null && e.getRuleTargetId().equals(callbackId))
                .collect(Collectors.toList());
    }

    /**
     * 计算规则目标成功或失败数
     *
     * @param list
     * @param ruleId
     * @param deliveryStatus
     * @return
     */
    public int getDeliveryStatusNum(List<EventDelivery> list, Integer ruleId,
                                    Integer deliveryStatus) {
        return (int) list.parallelStream()
                .filter(e -> e.getRuleId() != null && e.getRuleId().equals(ruleId)
                        && e.getDeliveryStatus().equals(deliveryStatus))
                .count();
    }

    @Override
    public IPage getEventDeliveryList(Page page, EventDeliveryQuery eventDeliveryQuery) {
        LambdaQueryWrapper<EventDelivery> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(eventDeliveryQuery.getEventUuid())) {
            wrapper.like(EventDelivery::getEventUuid, eventDeliveryQuery.getEventUuid());
        }
        wrapper.apply(StringUtils.isNotBlank(eventDeliveryQuery.getSearchBeginTime()),
                "date_format (creation_date,'%Y-%m-%d') >= date_format('" + eventDeliveryQuery
                        .getSearchBeginTime() + "','%Y-%m-%d')");
        wrapper.apply(StringUtils.isNotBlank(eventDeliveryQuery.getSearchEndTime()),
                "date_format (creation_date,'%Y-%m-%d') <= date_format('" + eventDeliveryQuery
                        .getSearchEndTime() + "','%Y-%m-%d')");
        wrapper.orderByDesc(EventDelivery::getCreationDate);
        wrapper.groupBy(EventDelivery::getEventId);
        wrapper.having("count(*) >= 1");
        return this.page(page, wrapper);
    }

    @Override
    public IPage getEventDeliveryFailList(Page page) {
        LambdaQueryWrapper<EventDelivery> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EventDelivery::getDeliveryStatus, DELIVERY_STATUS_FAIL);
        wrapper.groupBy(EventDelivery::getEventId);
        wrapper.having("count(*) >= 1");
        return this.page(page, wrapper);
    }

    @Override
    public boolean saveBatchEventDelivery(List<EventDelivery> eventDeliveryList) {
        if (ListUtil.isEmpty(eventDeliveryList)) {
            return false;
        }
        return this.saveBatch(eventDeliveryList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveEventDelivery(EventDelivery eventDelivery) {
        return this.save(eventDelivery);
    }

    @Async
    @Override
    public void saveEventDeliveryRun() {
        while (true) {
            try {
                //批量保存投递记录
                List<EventDelivery> eventDeliveryList = new ArrayList<>();
                //超过一定数据或时间进行批量保存里事件轨迹日志
                Queues.drain(eventDeliveryBlockingQueue, eventDeliveryList,
                        eventBridgeProperties.getNumElements(), eventBridgeProperties.getDuration(),
                        TimeUnit.MINUTES);
                this.saveBatchEventDelivery(eventDeliveryList);
            } catch (Exception e) {
                log.error("批量保存投递记录处理发生错误", e);
            }
        }
    }
}
