package com.sinosdx.service.event.bridge.controller.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@Getter
@Setter
@ToString
public class RuleQuery {

    @Length(max = 128)
    @ApiModelProperty("名称")
    private String name;

    @Length(max = 256)
    @ApiModelProperty("描述")
    private String description;

}
