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
package com.sinosdx.service.log.properties;

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
@ConfigurationProperties(prefix = LogConfig.GATEWAY_PREFIX)
public class LogConfig {

    public static final String GATEWAY_PREFIX = "sinosdx.log";

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * 时间间隔(秒)
     */
    private Integer interval = 60;

    /**
     * 次数
     */
    private Integer count = 30;

    /**
     * 黑名单时间(秒)
     */
    private long blacklistTime = 1800;

    /**
     * 加入黑名单时，邮件标题
     */
    private String emailSubject = "CSP2.0-技术中台-异常请求超过阈值";

    /**
     * 加入黑名单时，发送告警邮件
     */
    private String receiverList = "jiahu.peng@sinosdx.com";
}
