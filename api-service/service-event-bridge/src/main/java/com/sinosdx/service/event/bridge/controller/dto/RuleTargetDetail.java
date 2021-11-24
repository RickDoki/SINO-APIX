package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pengjiahu
 * @date 2021-01-12 14:32
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleTargetDetail extends RuleTarget {

    private Object targetInfo;
}
