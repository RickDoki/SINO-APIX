package com.sinosdx.service.event.bridge.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengjiahu
 * @date 2020-12-02 14:12
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TargetCallBO {

    @ApiModelProperty("事件id")
    private Integer eventId;

    @ApiModelProperty("事件标识")
    private String eventUuid;

    @ApiModelProperty("事件内容")
    private Object data;

    @ApiModelProperty("规则id")
    private Integer ruleId;

    @ApiModelProperty("目标id")
    private Integer ruleTargetId;

    @ApiModelProperty("回调目标id")
    private Integer callbackRuleTargetId;

    @ApiModelProperty("目标类型")
    private Integer targetType;

    @ApiModelProperty("目标")
    private String target;

    @ApiModelProperty("调用耗时")
    private Long timeConsumer;

    @ApiModelProperty("返回信息")
    private String response;
}
