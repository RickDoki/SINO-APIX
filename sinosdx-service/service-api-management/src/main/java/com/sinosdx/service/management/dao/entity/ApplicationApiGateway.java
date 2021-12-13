package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("application_api_gateway")
public class ApplicationApiGateway {
    private static final long serialVersionUID = -5843525684382545761L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * app的productId存在时为productId，不存在时为code
     */
    private String urlCode;
    private Integer appId;
    private Integer apiId;
    private String apiUrl;
    private String prefixPath;
    private String domain;
    private String gatewayId;
    private Integer isInternal;

}
