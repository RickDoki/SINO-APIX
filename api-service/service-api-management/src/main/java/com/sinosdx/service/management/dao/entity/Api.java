package com.sinosdx.service.management.dao.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("api")
public class Api extends Entity<Integer> {
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

    public Api(ApiVo apiVo) {
        this.id = apiVo.getApiId();
        this.name = apiVo.getApiName();
        this.description = apiVo.getDescription();
        this.markdown = apiVo.getMarkdown();
        this.domain = apiVo.getDomain().startsWith("http") ? apiVo.getDomain() : apiVo.getProtocol() + "://" + apiVo.getDomain();
        this.url = apiVo.getApiUrl();
        this.prefixPath = apiVo.getUpstreamPrefixPath() + apiVo.getPrefixPath();
        this.requestMethod = apiVo.getRequestMethod();
        this.requestParams = apiVo.getRequestParams();
        this.requestExample = apiVo.getRequestExample();
        this.responseParams = apiVo.getResponseParams();
        this.responseExample = apiVo.getResponseExample();
        this.version = apiVo.getApiVersion();
        this.isPublished = apiVo.getIsPublished();
        this.isInternal = apiVo.getIsInternal();
    }

    public Api(ApiVersion apiVersion) {
        this.id = apiVersion.getApiId();
        this.name = apiVersion.getApiName();
        this.description = apiVersion.getDescription();
        this.markdown = apiVersion.getMarkdown();
        this.domain = apiVersion.getDomain();
        this.url = apiVersion.getUrl();
        this.prefixPath = apiVersion.getPrefixPath();
        this.requestMethod = apiVersion.getRequestMethod();
        this.requestParams = apiVersion.getRequestParams();
        this.requestExample = apiVersion.getRequestExample();
        this.responseParams = apiVersion.getResponseParams();
        this.responseExample = apiVersion.getResponseExample();
        this.version = apiVersion.getVersion();
        this.isPublished = apiVersion.getIsPublished();
        this.isInternal = apiVersion.getIsInternal();
    }
}
