package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.RuleConverter;
import com.sinosdx.service.event.bridge.assembler.RuleModeConverter;
import com.sinosdx.service.event.bridge.assembler.RuleTargetConverter;
import com.sinosdx.service.event.bridge.controller.dto.*;
import com.sinosdx.service.event.bridge.controller.query.RuleQuery;
import com.sinosdx.service.event.bridge.dao.entity.*;
import com.sinosdx.service.event.bridge.dao.mapper.RuleMapper;
import com.sinosdx.service.event.bridge.enums.TargetTypeEnum;
import com.sinosdx.service.event.bridge.service.IRuleModeService;
import com.sinosdx.service.event.bridge.service.IRuleService;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.collection.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "rule")
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements IRuleService {

    @Autowired
    private IRuleModeService ruleModeService;

    @Autowired
    private IRuleTargetService ruleTargetService;

    @Autowired(required = false)
    private RuleConverter ruleConverter;

    @Autowired(required = false)
    private RuleModeConverter ruleModeConverter;

    @Autowired(required = false)
    private RuleTargetConverter ruleTargetConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRule(RuleSaveDTO ruleSaveDTO) {
        //参数校验
        if (this.checkExistName(ruleSaveDTO.getName(), null)) {
            AssertsUtil.fail("名称重复，请修改");
        }
        List<RuleSaveDTO.RuleTargetInfo> ruleTargetInfoList = ruleSaveDTO.getRuleTargetInfoList();
        if (ListUtil.isEmpty(ruleTargetInfoList)) {
            AssertsUtil.fail("事件目标不能为空");
        }
        //对象转换
        Rule rule = ruleConverter.saveDTOTo(ruleSaveDTO);
        RuleSaveDTO.RuleModeInfo ruleModeInfo = ruleSaveDTO.getRuleModeInfo();
        RuleMode ruleMode = ruleModeConverter.ruleModeInfoTo(ruleModeInfo);
        //事件规则
        this.save(rule);
        Integer eventRuleId = rule.getId();
        //事件模式
        ruleMode.setRuleId(eventRuleId);
        ruleModeService.saveRuleMode(ruleMode);
        //事件目标
        ruleTargetInfoList.forEach(e -> this.saveSingleTarget(e, eventRuleId));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSingleTarget(RuleSaveDTO.RuleTargetInfo callback, Integer ruleId) {
        //保存回调目标
        Integer callbackTargetId = null;
        RuleSaveDTO.CallbackRuleTargetInfo callbackTarget = callback.getCallbackRuleTargetInfo();
        if (callbackTarget != null) {
            callbackTargetId = saveCallback(callbackTarget);
        }
        return saveTarget(callback, ruleId, callbackTargetId);
    }

    @Override
    public Integer saveCallback(RuleSaveDTO.CallbackRuleTargetInfo callbackTarget) {
        RuleTarget ruleTarget = ruleTargetConverter.callbackInfoTo(callbackTarget);
        ruleTargetService.saveRuleTarget(ruleTarget);
        Integer ruleId = ruleTarget.getId();
        RuleSaveDTO.RuleTargetInfo targetInfo = ruleTargetConverter.callbackInfoToInfo(callbackTarget);
        saveTargetInfo(targetInfo, ruleId);
        return ruleId;
    }

    @Override
    public boolean saveTarget(RuleSaveDTO.RuleTargetInfo callback, Integer ruleId, Integer callbackTargetId) {
        //规则目标
        RuleTarget ruleTarget = ruleTargetConverter.infoTo(callback);
        ruleTarget.setRuleId(ruleId);
        if (callbackTargetId != null) {
            ruleTarget.setCallbackRuleTargetId(callbackTargetId);
        }
        ruleTargetService.saveRuleTarget(ruleTarget);
        //事件目标配置
        Integer eventRuleTargetId = ruleTarget.getId();
        return saveTargetInfo(callback, eventRuleTargetId);
    }

    public boolean saveTargetInfo(RuleSaveDTO.RuleTargetInfo targetInfo, Integer ruleTargetId) {
        if (ruleTargetId == null) {
            AssertsUtil.fail("saveTargetInfo methed ruleTargetId is null");
        }
        boolean result = true;
        String errorType = "targetType类型为:" + targetInfo.getTargetType() + ",";
        switch (TargetTypeEnum.formType(targetInfo.getTargetType())) {
            case MQ:
                RuleTargetMq ruleTargetMq = targetInfo.getRuleTargetMq();
                if (ruleTargetMq == null) {
                    AssertsUtil.fail(errorType + "ruleTargetMq不能为空");
                }
                if (StringUtils.isBlank(ruleTargetMq.getQueue())) {
                    AssertsUtil.fail("队列不能为空");
                }
                ruleTargetMq.setRuleTargetId(ruleTargetId);
                result = ruleTargetService.saveRuleTargetMq(ruleTargetMq);
                break;
            case HTTP:
                RuleTargetHttp ruleTargetHttp = targetInfo.getRuleTargetHttp();
                if (ruleTargetHttp == null) {
                    AssertsUtil.fail(errorType + "ruleTargetHttp不能为空");
                }
                if (StringUtils.isBlank(ruleTargetHttp.getUrl())) {
                    AssertsUtil.fail("URL不能为空");
                }
                ruleTargetHttp.setRuleTargetId(ruleTargetId);
                result = ruleTargetService.saveRuleTargetHttp(ruleTargetHttp);
                break;

            case DINGTALK_NOTICE:
                RuleTargetDingTalkNotice dingTalkNotice = targetInfo.getRuleTargetDingTalkNotice();
                if (dingTalkNotice == null) {
                    AssertsUtil.fail(errorType + "dingTalkNotice不能为空");
                }
                dingTalkNotice.setRuleTargetId(ruleTargetId);
                result = ruleTargetService.saveRuleTargetDingTalk(dingTalkNotice);
                break;
            case WORK_WEIXIN_NOTICE:
                RuleTargetWorkWeixinNotice workWeixinNotice = targetInfo
                        .getRuleTargetWorkWeixinNotice();
                if (workWeixinNotice == null) {
                    AssertsUtil.fail(errorType + "workWeixinNotice不能为空");
                }
                workWeixinNotice.setRuleTargetId(ruleTargetId);
                result = ruleTargetService.saveRuleTargetWorkWeixin(workWeixinNotice);
                break;
            default:
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#ruleUpdateDTO.id", allEntries = true)
    public boolean updateRule(RuleUpdateDTO ruleUpdateDTO) {
        if (this.getRuleDetail(ruleUpdateDTO.getId()) == null) {
            AssertsUtil.fail("该事件总线不存在");
        }
        if (this.checkExistName(ruleUpdateDTO.getName(), ruleUpdateDTO.getId())) {
            AssertsUtil.fail("名称重复，请修改");
        }
        return this.updateById(ruleConverter.updateDTOTo(ruleUpdateDTO));
    }

    @Override
    @Cacheable(key = "#eventRuleId", unless = "#result == null")
    public RuleDTO getRuleDetail(Integer eventRuleId) {
        Rule rule = this.getById(eventRuleId);
        if (rule == null) {
            return null;
        }
        RuleDTO ruleDTO = ruleConverter.toDTO(rule);
        //事件规则模式
        ruleDTO.setRuleMode(ruleModeService.getRuleModeByRuleId(eventRuleId));
        //事件目标
        ruleDTO.setRuleTargetDetailList(ruleTargetList(eventRuleId));
        return ruleDTO;
    }

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public Rule getRuleById(Integer id) {
        return this.getById(id);
    }

    public List<RuleTargetDetail> ruleTargetList(Integer eventRuleId) {
        List<RuleTarget> targetList = ruleTargetService.getRuleTargetListByRuleId(eventRuleId);
        //事件目标
        List<RuleTargetDetail> targetInfoList = new ArrayList<>();
        targetList.forEach(e -> {
            RuleTargetDetail ruleTargetDetail = ruleTargetConverter.toDetail(e);
            ruleTargetDetail
                    .setTargetInfo(ruleTargetService.getTargetInfo(e.getTargetType(), e.getId()));
            targetInfoList.add(ruleTargetDetail);
        });
        return targetInfoList;

    }

    @Override
    public IPage getRuleList(Integer busId, Page page, RuleQuery ruleQuery) {
        LambdaQueryWrapper<Rule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Rule::getBusId, busId);
        if (StringUtils.isNotBlank(ruleQuery.getName())) {
            wrapper.like(Rule::getName, ruleQuery.getName());
        }
        if (StringUtils.isNotBlank(ruleQuery.getDescription())) {
            wrapper.like(Rule::getDescription, ruleQuery.getDescription());
        }
        wrapper.orderByDesc(Rule::getCreationDate);
        return this.page(page, wrapper).convert(rule -> {
            RuleListDTO ruleListDTO = new RuleListDTO();
            BeanUtils.copyProperties(rule, ruleListDTO);
            ruleListDTO.setRuleTargetDetailList(this.ruleTargetList(ruleListDTO.getId()));
            return ruleListDTO;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#eventRuleId", allEntries = true)
    public boolean deleteRule(Integer eventRuleId) {
        return this.removeById(eventRuleId);
    }

    @Override
    public boolean checkExistName(String name, Integer excludeId) {
        LambdaQueryWrapper<Rule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Rule::getName, name);
        if (excludeId != null) {
            wrapper.ne(Rule::getId, excludeId);
        }
        return this.list(wrapper).size() > 0;
    }
}
