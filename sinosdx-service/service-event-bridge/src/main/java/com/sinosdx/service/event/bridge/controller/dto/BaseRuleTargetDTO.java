package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.common.base.annotation.ValueIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseRuleTargetDTO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("回调目标id")
    private Integer callbackRuleTargetId;

    @NotNull(message = "目标类型不能为空")
    @ValueIn(values = {"1", "2", "3", "4", "5", "6", "7",
            "8"}, message = "取值范围必须是 1：MQ  2：Http  3：短信  4：邮件")
    @ApiModelProperty("事件目标类型（1：MQ  2：Http  3：短信  4：邮件）")
    private Integer targetType;

    @NotNull(message = "转换类型不能为空")
    @ValueIn(values = {"1", "2", "3", "4"}, message = "取值范围必须是 1：完整事件  2：部分事件  3：常量  4：模板")
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

    @ApiModelProperty("目标详情")
    private Object targetInfo;

}
