package com.sinosdx.service.event.bridge.controller.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDeliveryDTO {

    @ApiModelProperty("事件uuid")
    private String eventUuid;

    @ApiModelProperty("事件源")
    private String source;

    @ApiModelProperty("事件类型标识")
    private String type;

    @ApiModelProperty("事件总线名称")
    private String busName;

    @ApiModelProperty("事件创建时间")
    private LocalDateTime createTime;

    private List<RuleInfo> ruleInfoList;

    @Getter
    @Setter
    public static class RuleInfo {

        @ApiModelProperty("规则Id")
        private Integer ruleId;

        @ApiModelProperty("规则名称")
        private String eventName;

        @ApiModelProperty("成功目标数")
        private Integer successNum;

        @ApiModelProperty("失败目标数")
        private Integer failNum;

        private List<EventDeliveryInfo> eventDeliveryInfoList;

        @Getter
        @Setter
        public static class EventDeliveryInfo extends BaseEventDeliveryInfo {

            private List<BaseEventDeliveryInfo> eventDeliveryCallbackList;
        }
    }

    @Getter
    @Setter
    public static class BaseEventDeliveryInfo {

        @ApiModelProperty("目标")
        private String target;

        @ApiModelProperty("投递状态，成功：1，失败：2")
        private Integer deliveryStatus;

        @ApiModelProperty("投递详情")
        private String description;

        @JsonIgnore
        @JSONField(serialize = false)
        @ApiModelProperty("回调目标id")
        private Integer callbackRuleTargetId;
    }
}
