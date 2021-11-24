package com.sinosdx.service.event.bridge.dao.entity;

import com.google.common.base.Preconditions;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

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
@ApiModel(value = "规则目标HTTP配置表")
public class RuleTargetHttp extends TargetCallProcessBase {

    @ApiModelProperty("规则目标id")
    private Integer ruleTargetId;

    @URL
    @Length(max = 128)
    @ApiModelProperty("URL")
    private String url;

    @Length(max = 10)
    @ApiModelProperty("数据请求方式,BODY、PARAMS")
    private String dataRequestWay;

    @Override
    public void paramCheck() {
        Preconditions.checkNotNull(this.getUrl(), "Http目标URL不能为空");
    }

    @Override
    public String defaultTarget() {
        return this.url;
    }
}
