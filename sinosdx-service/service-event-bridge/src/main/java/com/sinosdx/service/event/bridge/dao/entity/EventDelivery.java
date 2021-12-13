package com.sinosdx.service.event.bridge.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author pengjiahu
 * @date 2020-11-24 16:06
 * @description
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "事件投递轨迹表")
public class EventDelivery extends Entity<Integer> {

    @ApiModelProperty("事件id")
    private Integer eventId;

    @ApiModelProperty("事件uuid")
    private String eventUuid;

    @ApiModelProperty("事件规则id")
    private Integer ruleId;

    @ApiModelProperty("事件规则目标id")
    private Integer ruleTargetId;

    @ApiModelProperty("回调目标id")
    private Integer callbackRuleTargetId;

    @ApiModelProperty("目标类型")
    private Integer targetType;

    @ApiModelProperty("目标")
    private String target;

    @ApiModelProperty("投递状态，成功：1，失败：2")
    private Integer deliveryStatus;

    @ApiModelProperty("投递详情")
    private String description;

    @ApiModelProperty("投递内容")
    private String data;

    @ApiModelProperty("返回内容")
    private String response;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("目标处理完成时间")
    private LocalDateTime processCompleteDate;

}
