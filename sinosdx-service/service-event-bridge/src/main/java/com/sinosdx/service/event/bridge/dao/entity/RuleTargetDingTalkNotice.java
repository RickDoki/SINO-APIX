package com.sinosdx.service.event.bridge.dao.entity;

import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author pengjiahu
 * @date 2020-11-24 16:06
 * @description
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "规则目标钉钉机器人通知配置表")
public class RuleTargetDingTalkNotice extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

//    @ApiModelProperty("accessToken")
//    private String accessToken;
//
//    @ApiModelProperty("msgType（markdown、text、link）")
//    private String msgType;

    @Override
    public void paramCheck() {

    }

    @Override
    public String defaultTarget() {
        return "钉钉机器人通知";
    }
}
