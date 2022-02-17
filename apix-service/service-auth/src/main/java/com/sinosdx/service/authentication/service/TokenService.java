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
package com.sinosdx.service.authentication.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.controller.dto.GenerateTokenDto;
import com.sinosdx.service.authentication.dao.entity.ClientAppSecret;

import java.util.List;

/**
 * @author wendy
 * @date 2022/1/5
 */
public interface TokenService {

    /**
     * 给订阅方生成jwt
     *
     * @param generateTokenDto
     * @return
     */
    R<Object> generateJwt(GenerateTokenDto generateTokenDto);

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    R<Object> saveClientAppSecretKey(ClientAppSecret secret);

    /**
     * 查询客户端的secret
     *
     * @param secretKey
     * @return
     */
    R<ClientAppSecret> queryBySecret(String secretKey);

    /**
     * 给订阅方生成basic_token
     *
     * @param generateTokenDto
     * @return
     */
    R<Object> generateBasicToken(GenerateTokenDto generateTokenDto);

    /**
     * 查询客户端的secret
     *
     * @param appCode
     * @return
     */
    R<List<ClientAppSecret>> querySecretByAppCode(String appCode);

    /**
     * 删除secret
     *
     * @param appCode
     * @return
     */
    R<Object> deleteClientAppSecret(String appCode);
}
