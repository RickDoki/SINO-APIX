package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.BusConverter;
import com.sinosdx.service.event.bridge.controller.dto.BusDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.BusQuery;
import com.sinosdx.service.event.bridge.dao.entity.Bus;
import com.sinosdx.service.event.bridge.dao.mapper.BusMapper;
import com.sinosdx.service.event.bridge.service.IBusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "bus")
public class BusServiceImpl extends ServiceImpl<BusMapper, Bus> implements IBusService {

    @Autowired(required = false)
    private BusConverter busConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBus(BusSaveDTO busSaveDTO) {
        if (this.checkExistName(busSaveDTO.getName(), null)) {
            AssertsUtil.fail("已存在相同事件总线名称");
        }
        return this.save(busConverter.saveDTOToBus(busSaveDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#eventBusUpdateDTO.id", allEntries = true)
    public boolean updateBus(BusUpdateDTO eventBusUpdateDTO) {
        if (getBusDetail(eventBusUpdateDTO.getId()) == null) {
            AssertsUtil.fail("该事件总线不存在");
        }
        if (this.checkExistName(eventBusUpdateDTO.getName(), eventBusUpdateDTO.getId())) {
            AssertsUtil.fail("名称重复，请修改");
        }
        return this.updateById(busConverter.updateDTOToBus(eventBusUpdateDTO));
    }

    @Override
    @Cacheable(key = "#eventBusId", unless = "#result == null")
    public BusDTO getBusDetail(Integer eventBusId) {
        return Optional.ofNullable(this.getById(eventBusId)).map(bus -> busConverter.toBusDTO(bus))
                .orElse(null);
    }

    @Override
    public IPage getBusList(Page page, BusQuery busQuery) {
        LambdaQueryWrapper<Bus> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(busQuery.getName())) {
            wrapper.like(Bus::getName, busQuery.getName());
        }
        if (StringUtils.isNotBlank(busQuery.getDescription())) {
            wrapper.like(Bus::getDescription, busQuery.getDescription());
        }
        wrapper.orderByDesc(Bus::getCreationDate);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#eventBusId", allEntries = true)
    public boolean deleteBus(Integer eventBusId) {
        return this.removeById(eventBusId);
    }

    @Override
    public Bus getBusByBusName(String busName) {
        return new LambdaQueryChainWrapper<>(baseMapper).eq(Bus::getName, busName).last("LIMIT 1")
                .one();
    }

    @Override
    public boolean checkExistName(String name, Integer excludeId) {
        LambdaQueryWrapper<Bus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bus::getName, name);
        if (excludeId != null) {
            wrapper.ne(Bus::getId, excludeId);
        }
        return this.list(wrapper).size() > 0;
    }
}
