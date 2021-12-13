package com.sinosdx.service.event.bridge.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
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
@ApiModel(value = "事件表")
public class Event extends Entity<Integer> implements Serializable {

    private static final long serialVersionUID = 1905187165389251207L;

    @ApiModelProperty("事件标识")
    private String eventUuid;

    @ApiModelProperty("事件源")
    private String source;

    @ApiModelProperty("事件内容")
    private Object data;

    @ApiModelProperty("规格版本，默认1.0")
    private String specVersion;

    @ApiModelProperty("事件类型标识")
    private String type;

    @ApiModelProperty("事件总线id")
    private Integer busId;

    @ApiModelProperty("事件总线名称")
    private String busName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("接收事件的时间")
    private LocalDateTime receiveTime = LocalDateTime.now();

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("事件创建时间")
    private LocalDateTime createTime = LocalDateTime.now();

    @ApiModelProperty("处理状态：1：已处理（默认）2：未匹配到规则")
    private Integer processStatus;
}
