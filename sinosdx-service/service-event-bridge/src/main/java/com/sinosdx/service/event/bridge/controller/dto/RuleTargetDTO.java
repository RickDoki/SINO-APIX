package com.sinosdx.service.event.bridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleTargetDTO extends BaseRuleTargetDTO {

    private BaseRuleTargetDTO callbackRuleTarget;

}
