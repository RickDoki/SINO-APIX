package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.service.event.bridge.controller.dto.RuleDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.Rule;
import org.mapstruct.Mapper;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface RuleConverter {

    Rule saveDTOTo(RuleSaveDTO ruleSaveDTO);

    Rule updateDTOTo(RuleUpdateDTO ruleUpdateDTO);

    RuleDTO toDTO(Rule rule);
}
