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
package com.sinosdx.service.user.consumer;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wendy
 * @date 2021/6/15
 */
@FeignClient(value = "service-market")
public interface IamServiceFeign {

    /**
     * 获取iam user_token和openid
     * @param jsonObject
     * @return
     */
    @PostMapping("/iam/get-user-token")
    R<JSONObject> getUserTokenByMobile(@RequestBody JSONObject jsonObject);

    /**
     * 根据jwt和加密后的用户信息获取c01 digestKey
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/iam/get-c01-digest-key")
    String getDigestKeyByJwtAndEncrypt(@RequestBody JSONObject jsonObject);

    /**
     * 获取IAM二级用户信息
     *
     * @param mobile
     * @param userToken
     * @return
     */
    @GetMapping("/iam/query-second-user-info")
    R<JSONObject> querySecondUserInfoFromToken(@RequestParam(value = "mobile", required = false) String mobile,
                                                  @RequestParam(value = "userToken", required = false) String userToken);
}
