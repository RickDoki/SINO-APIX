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
package com.sinosdx.service.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.UserJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2021/7/2
 */
@RestController
@RequestMapping("/user")
public class UserJwtController {

    @Autowired
    private UserJwtService userJwtService;

    /**
     * 获取jwt
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/getJwt")
    public R<String> getJwt(@RequestBody JSONObject jsonObject) {
        if (!jsonObject.containsKey("userToken") || !jsonObject.containsKey("userId")) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }
        return userJwtService.getJwt(jsonObject.getString("userToken"), jsonObject.getString("userId"));
    }

    /**
     * 通过jwt获取用户信息
     *
     * @param jwt
     * @return
     */
    @GetMapping("/userInfo/{jwt}")
    public R<JSONObject> getUserInfoByJwt(@PathVariable(value = "jwt") String jwt) {
        return userJwtService.getUserInfoByJwt(jwt);
    }

}
