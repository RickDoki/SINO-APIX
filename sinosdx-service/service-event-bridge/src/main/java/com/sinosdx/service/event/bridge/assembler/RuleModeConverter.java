package com.sinosdx.service.event.bridge.assembler;

import com.sinosdx.service.event.bridge.controller.dto.RuleModeDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeUpdateDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.dao.entity.RuleMode;
import org.mapstruct.Mapper;

/**
 * @author pengjiahu
 * @date 2020-12-05 20:58
 * @description
 */
@Mapper(componentModel = "spring")
public interface RuleModeConverter {

    RuleMode updateDTOTo(RuleModeUpdateDTO ruleModeUpdateDTO);

    RuleModeDTO toDTO(RuleMode ruleMode);

    RuleMode ruleModeInfoTo(RuleSaveDTO.RuleModeInfo ruleModeInfo);
}
