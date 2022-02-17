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
 * @date 2021/7/1
 */
public interface UserJwtService {

    /**
     * 获取csp2.0 JWT
     *
     * @param iamUserToken
     * @param userId soc user uuid
     * @return
     */
    R<String> getJwt(String iamUserToken, String userId);

    /**
     * 通过jwt获取用户信息
     *
     * @param jwt
     * @return
     */
    R<JSONObject> getUserInfoByJwt(String jwt);
}
