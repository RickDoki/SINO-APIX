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
package com.sinosdx.common.gateway.plugin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author pengjiahu
 * @date 2021-12-30 14:57
 * @description
 */
@Data
@Builder
@ToString
public class RequestInfo {

    /**
     * 请求唯一标识
     */
    private String requestNo;
    /**
     * 真实ip
     */
    private String ip;
    /**
     * 应用唯一标识code
     */
    private String appCode;
    /**
     * 请求路径
     */
    private String requestPath;

}
