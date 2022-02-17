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
package com.sinosdx.service.management.service;

import com.sinosdx.service.management.dao.entity.Api;

import java.util.List;

/**
 * @author wendy
 * @date 2022/1/12
 */
public interface AppApiGatewayService {

    /**
     * api发布到网关
     *
     * @param applicationId
     * @param apiList
     * @param appClientCode
     */
    void updateApiGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode);

    /**
     * 删除已发布的api网关
     *
     * @param applicationId
     * @param apiList
     * @param appClientCode
     */
    void deleteApiGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode);
}
