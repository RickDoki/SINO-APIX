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
package com.sinosdx.service.management.controller.vo;

import com.sinosdx.service.management.dao.entity.ApplicationVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wendy
 * @date 2020/12/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersionVo implements Serializable {
    private static final long serialVersionUID = 677629965656735742L;

    private Integer appId;
    private String appCode;
    private Integer appVersionId;
    private String appVersion;
    private String versionDesc;
    private String apiIds;
    private String markdown;
    private List<ApiVo> apiList;

    public ApplicationVersionVo(ApplicationVersion applicationVersion, List<ApiVo> apiVoList) {
        this.appId = applicationVersion.getAppId();
        this.appCode = applicationVersion.getAppCode();
        this.appVersionId = applicationVersion.getId();
        this.appVersion = applicationVersion.getVersion();
        this.versionDesc = applicationVersion.getDescription();
        this.markdown = applicationVersion.getMarkdown();
        this.apiList = apiVoList;
    }
}
