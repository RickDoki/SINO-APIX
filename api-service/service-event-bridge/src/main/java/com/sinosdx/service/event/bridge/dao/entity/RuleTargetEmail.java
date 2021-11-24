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
@ApiModel(value = "规则目标邮箱配置表")
public class RuleTargetEmail extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("发信地址")
    private String accountName;

    @ApiModelProperty("收件人")
    private String toAddress;

    @ApiModelProperty("是否HTML正文")
    private Integer isHtmlBody;

    @ApiModelProperty("正文")
    private String body;

    @Override
    public void paramCheck() {

    }

    @Override
    public String defaultTarget() {
        return this.toAddress;
    }
}
