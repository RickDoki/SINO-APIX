package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.common.base.annotation.ValueIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
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
public class RuleUpdateDTO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Integer id;

    @Length(max = 128)
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;

    @Length(max = 256)
    @ApiModelProperty("描述")
    private String description;

    @ValueIn(values = {"1", "2"}, message = "取值范围必须是 启用：1，禁用：2")
    @ApiModelProperty("规则状态，启用：1，禁用：2")
    private Integer ruleStatus;

}
