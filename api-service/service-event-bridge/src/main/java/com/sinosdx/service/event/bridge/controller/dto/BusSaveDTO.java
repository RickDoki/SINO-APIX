package com.sinosdx.service.event.bridge.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusSaveDTO {

    @Length(max = 128)
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;

    @Length(max = 256)
    @ApiModelProperty("描述")
    private String description;

}
