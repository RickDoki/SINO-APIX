package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.service.event.bridge.dao.entity.Rule;
import com.sinosdx.service.event.bridge.dao.entity.RuleMode;
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
public class RuleDTO extends Rule {

    private RuleMode ruleMode;

    private List<RuleTargetDetail> ruleTargetDetailList;

}
