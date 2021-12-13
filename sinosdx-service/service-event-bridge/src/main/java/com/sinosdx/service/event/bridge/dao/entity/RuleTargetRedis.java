package com.sinosdx.service.event.bridge.dao.entity;

import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(value = "规则目标缓存redis表")
public class RuleTargetRedis extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @Length(max = 52)
    @ApiModelProperty("key")
    private String key;

    @Length(max = 32)
    @ApiModelProperty("过期时间（秒）")
    private Long expiredTime;

    @Override
    public void paramCheck() {

    }

    @Override
    public String defaultTarget() {
        return this.key;
    }
}
