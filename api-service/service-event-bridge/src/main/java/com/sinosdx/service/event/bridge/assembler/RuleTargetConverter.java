package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetDetail;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetHttp;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetMq;
import org.mapstruct.Mapper;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface RuleTargetConverter {

    RuleTargetDetail toDetail(RuleTarget ruleTarget);

    RuleTargetMq infoToRuleTargetMq(RuleSaveDTO.RuleTargetInfo ruleTargetInfo);

    RuleTargetHttp infoToRuleTargetHttp(RuleSaveDTO.RuleTargetInfo ruleTargetInfo);

    RuleTarget infoTo(RuleSaveDTO.RuleTargetInfo ruleTargetInfo);

    RuleTarget updateDTOTo(RuleTargetUpdateDTO ruleTargetUpdateDTO);

    RuleTargetDTO toDTO(RuleTarget ruleTarget);

    RuleTarget callbackInfoTo(RuleSaveDTO.CallbackRuleTargetInfo callbackRuleTargetInfo);

    RuleSaveDTO.RuleTargetInfo callbackInfoToInfo(RuleSaveDTO.CallbackRuleTargetInfo callbackRuleTargetInfo);

    RuleTargetUpdateDTO callbackToUpdate(RuleTargetUpdateDTO.CallbackRuleTargetUpdateDTO callbackRuleTargetUpdateDTO);

    RuleSaveDTO.CallbackRuleTargetInfo callbackTo(RuleTargetUpdateDTO.CallbackRuleTargetUpdateDTO callbackRuleTargetUpdateDTO);

}
