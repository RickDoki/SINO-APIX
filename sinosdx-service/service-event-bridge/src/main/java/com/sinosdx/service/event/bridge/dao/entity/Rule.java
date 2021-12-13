package com.sinosdx.service.event.bridge.dao.entity;

import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ApiModel(value = "规则表")
public class Rule extends Entity<Integer> {

    @ApiModelProperty("事件总线id")
    private Integer busId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("规则状态，启用：1，禁用：2")
    private Integer ruleStatus;

}
