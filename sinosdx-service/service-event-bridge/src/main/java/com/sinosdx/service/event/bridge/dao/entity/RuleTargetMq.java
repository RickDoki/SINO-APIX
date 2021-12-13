package com.sinosdx.service.event.bridge.dao.entity;

import com.google.common.base.Preconditions;
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
@ApiModel(value = "规则目标MQ表")
public class RuleTargetMq extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @Length(max = 52)
    @ApiModelProperty("队列")
    private String queue;

    @Override
    public void paramCheck() {
        Preconditions.checkNotNull(this.getQueue(), "MQ目标队列不能为空");
    }

    @Override
    public String defaultTarget() {
        return this.queue;
    }
}
