package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.EventConverter;
import com.sinosdx.service.event.bridge.controller.dto.EventDTO;
import com.sinosdx.service.event.bridge.dao.entity.Bus;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.mapper.EventMapper;
import com.sinosdx.service.event.bridge.event.publisher.EventPublisher;
import com.sinosdx.service.event.bridge.service.IBusService;
import com.sinosdx.service.event.bridge.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sinosdx.service.event.bridge.constants.CommonConstant.PROCESS_STATUS_NO;


/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "event")
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {

    @Autowired
    private IBusService busService;

    @Autowired(required = false)
    private EventConverter eventConverter;

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public EventDTO getEventDetail(Integer id) {
        return Optional.ofNullable(this.getById(id)).map(event -> eventConverter.toDTO(event))
                .orElse(null);
    }

    @Override
    @CacheEvict(key = "#id", allEntries = true)
    public boolean modifyEventProcessStatus(Integer id) {
        LambdaQueryWrapper<Event> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Event::getId, id);
        return this.update(Event.builder().processStatus(PROCESS_STATUS_NO).build(), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveEvent(CloudEvent cloudEvent) {
        Event event = eventConverter.cloudEventTo(cloudEvent);
        Bus bus = busService.getBusByBusName(cloudEvent.getBusName());
        if (bus == null) {
            AssertsUtil.fail("未找到【" + cloudEvent.getBusName() + "】总线");
        }
        event.setBusId(bus.getId());
        if (!this.save(event)) {
            AssertsUtil.fail("事件保存失败");
        }
        EventPublisher.publishEvent(event);
        return event;
    }
}
