package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.RuleModeConverter;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.RuleMode;
import com.sinosdx.service.event.bridge.dao.mapper.RuleModeMapper;
import com.sinosdx.service.event.bridge.service.IRuleModeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "rule_mode")
public class RuleModeServiceImpl extends ServiceImpl<RuleModeMapper, RuleMode> implements
        IRuleModeService {

    @Autowired(required = false)
    private RuleModeConverter ruleModeConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#ruleModeUpdateDTO.id", allEntries = true)
    public boolean updateRuleMode(RuleModeUpdateDTO ruleModeUpdateDTO) {
        if (this.getRuleModeByRuleId(ruleModeUpdateDTO.getId()) == null) {
            AssertsUtil.fail("该规则模式不存在");
        }
        RuleMode ruleMode = ruleModeConverter.updateDTOTo(ruleModeUpdateDTO);
        return this.updateById(ruleMode);
    }

    @Override
    @Cacheable(key = "RuleMode+':'+#eventRuleModeId", unless = "#result == null")
    public RuleModeDTO getRuleModeDetail(Integer eventRuleModeId) {
        return Optional.ofNullable(this.getById(eventRuleModeId))
                .map(ruleMode -> ruleModeConverter.toDTO(ruleMode))
                .orElse(null);
    }

    @Override
    @Cacheable(key = "methodName+':'+#eventRuleId", unless = "#result == null")
    public RuleMode getRuleModeByRuleId(Integer eventRuleId) {
        return new LambdaQueryChainWrapper<>(baseMapper).eq(RuleMode::getRuleId, eventRuleId)
                .last("LIMIT 1").one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleMode(RuleMode ruleMode) {
        return this.save(ruleMode);
    }

    @Override
    public List<RuleMode> getRuleModeListBySourceAndType(String source, String type) {
        return new LambdaQueryChainWrapper<>(baseMapper)
                .eq(RuleMode::getSource, source).eq(RuleMode::getType, type).list();
    }
}
