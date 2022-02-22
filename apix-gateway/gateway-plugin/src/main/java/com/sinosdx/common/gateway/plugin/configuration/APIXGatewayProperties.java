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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengjiahu
 * @date 2019/11/20 15:13
 * @description
 */
@Getter
@Setter
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = APIXGatewayProperties.GATEWAY_PREFIX)
public class APIXGatewayProperties {

    public static final String GATEWAY_PREFIX = "apix.gateway";

    /**
     * 是否启用帮助模式，有助于问题排查，默认不启用
     */
    private boolean help = false;
}
