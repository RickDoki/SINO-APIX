package com.sinosdx.service.event.bridge.controller.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@Getter
@Setter
@ToString
public class DataParsingQuery {

    @NotBlank(message = "data不能为空")
    @ApiModelProperty("data")
    private String data;

    @ApiModelProperty("expression")
    private String expression;

    @ApiModelProperty("template")
    private String template;

}
