package com.sinosdx.service.management.controller.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import com.sinosdx.service.management.controller.vo.ApiVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
public class ApiDto  {
    private static final long serialVersionUID = -1756838965319678436L;

    private String name;
    private String description;
    private String markdown;
    private String version;
    private String domain;
    private String url;
    private String prefixPath;
    private String requestMethod;
    private String requestParams;
    private String requestExample;
    private String responseParams;
    private String responseExample;
    private String isPublished;
    /**
     * 是否为中台内部接口：0否1是
     */
    private Integer isInternal;
    private String creationByUsername;
    private String lastUpdatedByUsername;

    private String creationDate;
    private String lastUpdateDate;
    private String protocol;
    private Long port;
    private String apiName;
    private Integer apiId;
    private Integer upstreamId;
    private String upstreamPrefixPath;
    /**
     * 前端传值域名，不拼接协议和端口
     */
    private String simpleDomain;
    /**
     * 前端传值前置路径，不拼接上游前置路径
     */
    private String simplePrefixPath;

}
