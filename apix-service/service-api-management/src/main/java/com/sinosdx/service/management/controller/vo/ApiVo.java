package com.sinosdx.service.management.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sinosdx.service.management.dao.entity.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wendy
 * @date 2020/12/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiVo implements Serializable {
    private static final long serialVersionUID = -7113454164502943141L;

    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private Integer apiId;
    private String apiName;
    private String description;
    private String markdown;
    private String domain;
    private Long port;
    private String apiUrl;
    private String prefixPath;
    private String upstreamPrefixPath;
    private String protocol;
    private String requestMethod;
    private String requestParams;
    private String requestExample;
    private String responseParams;
    private String responseExample;
    private String apiVersion;
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String isPublished;
    private Integer isInternal;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    private String creationByUsername;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private String lastUpdatedByUsername;
    private Integer upstreamId;

    public ApiVo(Api api) {
        this.apiId = api.getId();
        this.apiName = api.getName();
        this.description = api.getDescription();
        this.markdown = api.getMarkdown();
        this.domain = api.getDomain();
        this.apiUrl = api.getUrl();
        this.prefixPath = api.getPrefixPath();
        this.requestMethod = api.getRequestMethod();
        this.requestParams = api.getRequestParams();
        this.requestExample = api.getRequestExample();
        this.responseParams = api.getResponseParams();
        this.responseExample = api.getResponseExample();
        this.apiVersion = api.getVersion();
        this.isPublished = api.getIsPublished();
        this.isInternal = api.getIsInternal();
        this.creationDate = api.getCreationDate();
        this.creationByUsername = api.getCreationByUsername();
        this.lastUpdateDate = api.getLastUpdateDate();
        this.lastUpdatedByUsername = api.getLastUpdatedByUsername();
        this.protocol = api.getProtocol();
        this.port = api.getPort();
        this.upstreamId = api.getUpstreamId();
    }
}
