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
package com.sinosdx.service.authentication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.QywxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wendy
 * @date 2021/1/12
 */
@Slf4j
@RefreshScope
@Service
public class QywxServiceImpl implements QywxService {

    @Value("${qywx.corpid:wx963179f52abc1968}")
    private String corpId;

    @Value("${qywx.corpsecret:FL9IkH6t1YAxX32x7Xbkx6Ossxf7YzsdBhdiPb8mDk8}")
    private String corpSecret;

    @Value("${qywx.agentId:1000066}")
    private String agentId;

    /**
     * 获取access_token url
     */
    final private String getAccessTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    /**
     * 获取UserId url
     */
    final private String getUserIdUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";

    /**
     * 获取user info url
     */
    final private String getUserInfoUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取企业微信accessToken
     *
     * @return
     */
    @Override
    public String getAccessToken() {
        String url = String.format(getAccessTokenUrl, corpId, corpSecret);
        JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
        if (jsonObject.containsKey("access_token")) {
            return jsonObject.getString("access_token");
        } else {
            log.error("Get access token failed, result: " + jsonObject.toJSONString());
            return null;
        }
    }

    /**
     * 获取当前访问用户信息
     *
     * @param code
     * @return
     */
    @Override
    public R<JSONObject> getQywxUserInfo(String code) {
        String accessToken = getAccessToken();
        if (null == accessToken || null == code) {
            return R.fail();
        }

        // 获取UserId
        String userIdUrl = String.format(getUserIdUrl, accessToken, code);
        JSONObject userIdJsonObject = restTemplate.getForObject(userIdUrl, JSONObject.class);
        if (!userIdJsonObject.containsKey("UserId")) {
            log.error("无法获取企微用户userId，用户可能不在企业中");
            return R.fail(ResultCodeEnum.LOGIN_USER_NOT_COMPANY_USER);
        }
        String userId = userIdJsonObject.getString("UserId");

        // 获取企微用户信息
        String userInfoUrl = String.format(getUserInfoUrl, accessToken, userId);
        JSONObject userInfoJsonObject = restTemplate.getForObject(userInfoUrl, JSONObject.class);
        if (null == userInfoJsonObject || !userInfoJsonObject.containsKey("mobile")) {
            log.error(String.format("获取用户手机号码失败：%s", userInfoJsonObject));
            return R.fail();
        }
        return R.success(userInfoJsonObject);
    }
}
