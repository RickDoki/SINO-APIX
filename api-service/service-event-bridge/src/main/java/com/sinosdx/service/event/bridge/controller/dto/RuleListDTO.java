package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.service.event.bridge.dao.entity.Rule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleListDTO extends Rule {

    private List<RuleTargetDetail> ruleTargetDetailList;

}
