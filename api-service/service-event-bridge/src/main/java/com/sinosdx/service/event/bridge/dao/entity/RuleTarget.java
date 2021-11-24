package com.sinosdx.service.event.bridge.dao.entity;

import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author pengjiahu
 * @date 2020-11-24 16:06
 * @description
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "规则目标表")
public class RuleTarget extends Entity<Integer> implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    @ApiModelProperty("规则id")
    private Integer ruleId;

    @ApiModelProperty("回调目标id")
    private Integer callbackRuleTargetId;

    @ApiModelProperty("目标类型（1：MQ  2：Http  3：短信  4：邮件）")
    private Integer targetType;

    @ApiModelProperty("转换类型（1：完整事件  2：部分事件  3：常量  4：模板）")
    private Integer convertType;

    @ApiModelProperty("部分事件解析表达式")
    private String sectionExpression;

    @ApiModelProperty("常量值")
    private String constantValue;

    @ApiModelProperty("模板-变量或表达式")
    private String constantExpression;

    @ApiModelProperty("模板")
    private String template;

}
