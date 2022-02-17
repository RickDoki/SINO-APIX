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
@TableName("api_version")
public class ApiVersion extends Entity<Integer> {
    private static final long serialVersionUID = -1756838965319678436L;

    private Integer apiId;
    private String apiName;
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

    public ApiVersion(Api api) {
        this.apiId = api.getId();
        this.apiName = api.getName();
        this.description = api.getDescription();
        this.markdown = api.getMarkdown();
        this.version = api.getVersion();
        this.domain = api.getDomain();
        this.url = api.getUrl();
        this.prefixPath = api.getPrefixPath();
        this.requestMethod = api.getRequestMethod();
        this.requestParams = api.getRequestParams();
        this.requestExample = api.getRequestExample();
        this.responseParams = api.getResponseParams();
        this.responseExample = api.getResponseExample();
        this.isPublished = api.getIsPublished();
        this.isInternal = api.getIsInternal();
    }
}
