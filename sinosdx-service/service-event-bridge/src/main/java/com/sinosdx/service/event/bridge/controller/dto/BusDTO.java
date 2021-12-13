package com.sinosdx.service.event.bridge.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

}
