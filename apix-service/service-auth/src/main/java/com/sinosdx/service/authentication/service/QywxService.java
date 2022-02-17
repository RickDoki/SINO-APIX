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
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wendy
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/auth/qywx")
public interface QywxService {

    /**
     * 获取企业微信accessToken
     *
     * @return
     */
    @GetMapping("/access_token")
    String getAccessToken();

    /**
     * 获取当前访问用户信息
     *
     * @param code
     * @return
     */
    @GetMapping("/login/user/info")
    R<JSONObject> getQywxUserInfo(String code);

}
