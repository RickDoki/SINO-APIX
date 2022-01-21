package com.sinosdx.service.log.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author pengjiahu
 * @date 2020-09-17 12:16
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
public class AnalysisGatewayLog {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    protected Integer id;
    private String appCode;
    private String serviceId;
    private Long consumingTime;
    protected String requestPath;
    protected String requestUri;
    private Integer userId;
    private Long eventTime;
    private Integer statusCode;
    @TableField(exist = false)
    private String groupKey;
}
