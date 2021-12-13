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
public class RuleModeDTO {

    @Length(max = 128)
    @NotBlank(message = "事件源不能为空")
    @ApiModelProperty("事件源")
    private String source;

    @Length(max = 64)
    @NotBlank(message = "事件类型不能为空")
    @ApiModelProperty("事件类型")
    private String type;

    @ApiModelProperty("jsonPath过滤规则表达式")
    private String jsonPathExpression;

}
