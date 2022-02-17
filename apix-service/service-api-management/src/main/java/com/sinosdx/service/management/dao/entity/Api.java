/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    /**
     * 前端传值域名，不拼接协议和端口
     */
    private String simpleDomain;
    private String url;
    /**
     * 前端传值前置路径，不拼接上游前置路径
     */
    private String simplePrefixPath;
    private String upstreamPrefixPath;
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
    private String protocol;
    private Long port;
    private Integer upstreamId;

    public Api(ApiVo apiVo) {
        this.id = apiVo.getApiId();
        this.name = apiVo.getApiName();
        this.description = apiVo.getDescription();
        this.markdown = apiVo.getMarkdown();
        this.simpleDomain = apiVo.getDomain();
        // 域名拼接
        String domain = apiVo.getDomain();
        if (!domain.startsWith("http")) {
            domain = apiVo.getProtocol() + "://" + apiVo.getDomain();
        }
        if (!apiVo.getPort().equals(80L)) {
            domain += ":" + apiVo.getPort();
        }
        this.domain = domain;
        //        this.domain = apiVo.getDomain().startsWith("http") ? apiVo.getDomain() + ":" + apiVo.getPort() : apiVo.getProtocol() + "://" + apiVo.getDomain() + ":" + apiVo.getPort();
        this.url = apiVo.getApiUrl();
        this.upstreamPrefixPath = apiVo.getUpstreamPrefixPath();
        this.simplePrefixPath = apiVo.getPrefixPath();
        this.prefixPath = apiVo.getUpstreamPrefixPath() + apiVo.getPrefixPath();
        this.requestMethod = apiVo.getRequestMethod();
        this.requestParams = apiVo.getRequestParams();
        this.requestExample = apiVo.getRequestExample();
        this.responseParams = apiVo.getResponseParams();
        this.responseExample = apiVo.getResponseExample();
        this.version = apiVo.getApiVersion();
        this.isPublished = apiVo.getIsPublished();
        this.isInternal = apiVo.getIsInternal();
        this.protocol = apiVo.getProtocol();
        this.port = apiVo.getPort();
        this.upstreamId = apiVo.getUpstreamId();
    }

//    public Api(ApiVersion apiVersion) {
//        this.id = apiVersion.getApiId();
//        this.name = apiVersion.getApiName();
//        this.description = apiVersion.getDescription();
//        this.markdown = apiVersion.getMarkdown();
//        this.domain = apiVersion.getDomain();
//        this.url = apiVersion.getUrl();
//        this.prefixPath = apiVersion.getPrefixPath();
//        this.requestMethod = apiVersion.getRequestMethod();
//        this.requestParams = apiVersion.getRequestParams();
//        this.requestExample = apiVersion.getRequestExample();
//        this.responseParams = apiVersion.getResponseParams();
//        this.responseExample = apiVersion.getResponseExample();
//        this.version = apiVersion.getVersion();
//        this.isPublished = apiVersion.getIsPublished();
//        this.isInternal = apiVersion.getIsInternal();
//    }
}
