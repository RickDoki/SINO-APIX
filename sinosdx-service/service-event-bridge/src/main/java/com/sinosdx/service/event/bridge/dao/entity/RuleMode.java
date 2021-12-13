package com.sinosdx.service.event.bridge.dao.entity;

import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author pengjiahu
 * @date 2020-11-24 16:06
 * @description 事件通过模式进行过滤后，交由事件目标处理
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "规则模式表")
public class RuleMode extends Entity<Integer> {

    @ApiModelProperty("规则id")
    private Integer ruleId;

    @ApiModelProperty("事件源")
    private String source;

    @ApiModelProperty("事件类型标识")
    private String type;

    @ApiModelProperty("jsonPath过滤规则表达式")
    private String jsonPathExpression;
}
