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
@ApiModel(value = "规则目标内部服务接口配置表")
public class RuleTargetInnerApi extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @ApiModelProperty("url")
    private String url;

    @Override
    public void paramCheck() {

    }

    @Override
    public String defaultTarget() {
        return this.url;
    }
}
