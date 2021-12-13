package com.sinosdx.service.event.bridge.controller.query;

import com.sinosdx.common.base.base.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class EventDeliveryQuery extends BaseParam {

    @Length(max = 40)
    @ApiModelProperty("事件uuid")
    private String eventUuid;

}
