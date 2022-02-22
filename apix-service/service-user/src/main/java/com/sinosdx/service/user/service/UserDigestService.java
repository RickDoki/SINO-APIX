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
package com.sinosdx.service.user.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;

/**
 * @author wendy
 * @date 2021/6/15
 */
public interface UserDigestService {

    /**
     * 获取用户digestKey
     *
     * @param jwt
     * @param userData
     * @return
     */
    R<String> getUserDigestKey(String jwt, JSONObject userData);

    /**
     * 根据digest获取用户信息
     *
     * @param digestKey
     * @return
     */
    R<JSONObject> getUserDigestData(String digestKey);

    /**
     * 获取区域和用户信息
     *
     * @param region
     * @param serviceKey
     * @param digestKey
     * @return
     */
    R<JSONObject> getRegionAndUserInfo(String region, String serviceKey, String digestKey);

    /**
     * 区分c01和csp2.0请求获取digestKey
     *
     * @param jwt
     * @param encrypt
     * @return
     */
    R<Object> getDigestKeyForProxyGateway(String jwt, String encrypt);
}
