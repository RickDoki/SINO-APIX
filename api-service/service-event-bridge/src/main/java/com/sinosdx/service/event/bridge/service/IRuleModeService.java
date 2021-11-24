package com.sinosdx.service.event.bridge.service;


import com.sinosdx.service.event.bridge.controller.dto.RuleModeDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.RuleMode;

import java.util.List;

/**
 * 事件规则模式
 *
 * @author pengjiahu
 * @date 2020-11-29 10:34
 * @description
 */
public interface IRuleModeService {

    /**
     * 修改事件规则模式
     *
     * @param ruleModeUpdateDTO
     * @return
     */
    boolean updateRuleMode(RuleModeUpdateDTO ruleModeUpdateDTO);

    /**
     * 查询事件规则模式
     *
     * @param ruleModeId
     * @return
     */
    RuleModeDTO getRuleModeDetail(Integer ruleModeId);

    /**
     * 事件规则id
     *
     * @param eventRuleId
     * @return
     */
    RuleMode getRuleModeByRuleId(Integer eventRuleId);

    /**
     * 保存事件规则模式
     *
     * @param ruleMode
     * @return
     */
    boolean saveRuleMode(RuleMode ruleMode);

    /**
     * 根据事件源和事件类型标识查询规模模式列表
     *
     * @param source
     * @param type
     * @return
     */
    List<RuleMode> getRuleModeListBySourceAndType(String source, String type);
}
