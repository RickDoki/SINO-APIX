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
@ApiModel(value = "规则目标短信表")
public class RuleTargetSms extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @ApiModelProperty("手机号码")
    private Integer phoneNumber;

    @ApiModelProperty("签名名称")
    private String signName;

    @ApiModelProperty("模板CODE")
    private String templateCode;

    @ApiModelProperty("模板参数")
    private String templateParam;

    @Override
    public void paramCheck() {

    }

    @Override
    public String defaultTarget() {
        return String.valueOf(this.phoneNumber);
    }
}
