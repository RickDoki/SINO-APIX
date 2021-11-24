package com.sinosdx.service.event.bridge.controller.dto;

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
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleModeUpdateDTO extends RuleModeDTO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Integer id;

}
