package com.sinosdx.service.event.bridge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.service.event.bridge.controller.dto.RuleDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.RuleQuery;
import com.sinosdx.service.event.bridge.dao.entity.Rule;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:55
 * @description
 */
public interface IRuleService {

    /**
     * 保存事件规则
     *
     * @param ruleSaveDTO
     * @return
     */
    boolean saveRule(RuleSaveDTO ruleSaveDTO);

    /**
     * 保存规则单个目标
     *
     * @param callback
     * @param ruleId
     * @return
     */
    boolean saveSingleTarget(RuleSaveDTO.RuleTargetInfo callback, Integer ruleId);

    /**
     * 保存回调规则目标
     *
     * @param callbackTarget
     * @return
     */
    Integer saveCallback(RuleSaveDTO.CallbackRuleTargetInfo callbackTarget);

    /**
     * 保存规则目标
     *
     * @param callback
     * @param ruleId
     * @param callbackTargetId
     * @return
     */
    boolean saveTarget(RuleSaveDTO.RuleTargetInfo callback, Integer ruleId, Integer callbackTargetId);

    /**
     * 修改事件规则
     *
     * @param ruleUpdateDTO
     * @return
     */
    boolean updateRule(RuleUpdateDTO ruleUpdateDTO);

    /**
     * 查询事件规则
     *
     * @param ruleId
     * @return
     */
    RuleDTO getRuleDetail(Integer ruleId);

    /**
     * 查询事件规则
     *
     * @param busId
     * @param page
     * @param ruleQuery
     * @return
     */
    IPage getRuleList(Integer busId, Page page, RuleQuery ruleQuery);

    /**
     * 删除事件规则
     *
     * @param ruleId
     * @return
     */
    boolean deleteRule(Integer ruleId);

    /**
     * 根据规则名称查询规则详情
     *
     * @param name
     * @param excludeId 排除某个规则id
     * @return
     */
    boolean checkExistName(String name, Integer excludeId);

    /**
     * 根据Id查询规则
     *
     * @param id
     * @return
     */
    Rule getRuleById(Integer id);
}
