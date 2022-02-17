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
package com.sinosdx.common.gateway.plugin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengjiahu
 * @date 2021-06-15 17:25
 * @description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = IgnoreUrlConfig.PREFIX)
public class IgnoreUrlConfig {

    public static final String PREFIX = "mt.gateway";

    private List<String> ignoreUrls = new ArrayList<>();

    /**
     * 判断URL 是否自动跳过
     *
     * @param url url
     * @return 自动跳过
     */
    public boolean isIgnoreUrl(String url) {
        return ignoreUrls.stream().anyMatch(url::contains);
    }
}
