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
package com.sinosdx.service.authentication.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.context.ThreadContext;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.consumer.ApplicationServiceFeign;
import com.sinosdx.service.authentication.consumer.LoginFeign;
import com.sinosdx.service.authentication.consumer.SysUserServiceFeign;
import com.sinosdx.service.authentication.dao.entity.LoginHistory;
import com.sinosdx.service.authentication.dao.entity.LoginParam;
import com.sinosdx.service.authentication.exceptions.InvalidPasswordException;
import com.sinosdx.service.authentication.exceptions.UserBannedException;
import com.sinosdx.service.authentication.exceptions.UserNotRegisteredException;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.ImageVerificationCodeService;
import com.sinosdx.service.authentication.service.LoginHistoryService;
import com.sinosdx.service.authentication.service.QywxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wendy
 * @date 2020/12/23
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginFeign loginService;

    @Autowired
    private SysUserServiceFeign sysUserService;

    @Autowired
    private ApplicationServiceFeign applicationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private QywxService qywxService;

    @Autowired
    private LoginHistoryService loginHistoryService;

    @Autowired
    private ImageVerificationCodeService imageVerificationCodeService;

    /**
     * 登录接口（用户手机号码登录和客户端认证）
     *
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    public R<Object> authLogin(@RequestBody LoginParam loginParam) {
        Map<String, String> requestParams = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        // 用户名密码方式
        if (StringUtils.isAllEmpty(loginParam.getClientId(), loginParam.getCode())) {
            requestParams.put(Constants.LOGIN_GRANT_TYPE, "password");
            requestParams.put(Constants.LOGIN_TYPE, loginParam.getLoginType());
            requestParams.put(Constants.LOGIN_USERNAME, loginParam.getUsername());
            requestParams.put(Constants.LOGIN_PASSWORD, loginParam.getPassword());
            if ("admin".equals(loginParam.getUsername())) {
                // 测试账号用admin登录
                requestParams.put(Constants.LOGIN_USERNAME, Constants.LOGIN_ADMIN_PHONE);
                requestParams.put(Constants.LOGIN_CLIENT_ID, Constants.LOGIN_ADMIN_CLIENT_ID);
                requestParams.put(Constants.LOGIN_CLIENT_SECRET, Constants.LOGIN_ADMIN_CLIENT_SECRET);
            } else {
                // 前端用默认客户端信息登录
                requestParams.put(Constants.LOGIN_CLIENT_ID, Constants.LOGIN_FRONT_CLIENT_ID);
                requestParams.put(Constants.LOGIN_CLIENT_SECRET, Constants.LOGIN_FRONT_CLIENT_SECRET);

                // 验证图片验证码
//                R<Object> verifyResult =
//                        imageVerificationCodeService.verifyCode(loginParam.getVerifyCode(), loginParam.getUuid());
//                if (!(Boolean) verifyResult.getData()) {
//                    return verifyResult;
//                }
            }
        }
        // 企业微信扫码登录
        else if (StringUtils.isNotEmpty(loginParam.getCode()) && StringUtils.isEmpty(loginParam.getClientId())) {
            requestParams.put(Constants.LOGIN_GRANT_TYPE, "password");
            requestParams.put(Constants.LOGIN_TYPE, Constants.LOGIN_TYPE_QYWX);
            JSONObject data = qywxService.getQywxUserInfo(loginParam.getCode()).getData();
            log.info("qywx user info: " + data);
            if (null == data) {
                return R.fail(ResultCodeEnum.LOGIN_USER_NOT_COMPANY_USER);
            }
            requestParams.put(Constants.LOGIN_USERNAME, data.getString("mobile"));
            requestParams.put(Constants.LOGIN_CLIENT_ID, Constants.LOGIN_FRONT_CLIENT_ID);
            requestParams.put(Constants.LOGIN_CLIENT_SECRET, Constants.LOGIN_FRONT_CLIENT_SECRET);
        }
        // 客户端认证方式
        else {
            // 判断应用对接是否已解除
//            Boolean data = applicationService.verifyClientId(loginParam.getClientId()).getData();
//            if (null == data || !data) {
//                return R.fail(ResultCodeEnum.CLIENT_ID_ERROR);
//            }
            requestParams.put(Constants.LOGIN_GRANT_TYPE, "client_credentials");
            requestParams.put(Constants.LOGIN_CLIENT_ID, loginParam.getClientId());
            requestParams.put(Constants.LOGIN_CLIENT_SECRET, loginParam.getClientSecret());
        }

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        // 判断账号是否被锁定
        if (StringUtils.isBlank(loginParam.getClientId())
                && "LOCK".equals(opsForValue.get(Constants.LOGIN_ERROR_LOCK + loginParam.getUsername()))) {
            return R.fail(ResultCodeEnum.LOGIN_USERNAME_IS_LOCKED);
        }

        // 调用OAuth2获取token
        JSONObject data;
        String loginErrorCountKey = Constants.LOGIN_ERROR_COUNT + loginParam.getUsername();
        String loginErrorLockKey = Constants.LOGIN_ERROR_LOCK + loginParam.getUsername();
        try {
            data = loginService.requestToken(requestParams);
            resultMap.put("token", data.getString("value"));
            resultMap.put("expiresIn", data.getLong("expiresIn"));

            opsForValue.set(loginErrorCountKey, "0");
            opsForValue.set(loginErrorLockKey, "UNLOCK");
        } catch (UserBannedException e) {
            log.error(String.format("用户%s已被禁用", loginParam.getUsername()), e);
            return R.fail(ResultCodeEnum.USER_BANNED);
        } catch (UserNotRegisteredException e) {
            log.error(String.format("用户%s未注册", loginParam.getUsername()), e);
            return R.fail(ResultCodeEnum.USER_NOT_REGISTERED);
        } catch (InvalidPasswordException e) {
            log.error(String.format("用户%s密码错误", loginParam.getUsername()), e);
            if (null == loginParam.getClientId() && null == loginParam.getCode()) {
                opsForValue.increment(loginErrorCountKey, 1);
                redisTemplate.expire(loginErrorCountKey, 1, TimeUnit.HOURS);
                // 错误次数超过三次，锁定一小时
                if (null != opsForValue.get(loginErrorCountKey) && Integer.parseInt(opsForValue.get(loginErrorCountKey)) > 3) {
                    opsForValue.set(loginErrorLockKey, "LOCK");
                    redisTemplate.expire(loginErrorLockKey, 1, TimeUnit.HOURS);
                }
            }
            return R.fail(ResultCodeEnum.PWD_ERROR);
        } catch (Exception e) {
            log.error("登录失败：", e);
            return R.fail(ResultCodeEnum.USERNAME_OR_PWD_ERROR);
        }

        // 登录成功返回用户信息
        try {
            JSONObject information = data.getJSONObject("additionalInformation");
            if (information.containsKey("userId")) {
                resultMap.put("userId", information.getInteger("userId"));
                resultMap.put("username", information.getString("username"));
                resultMap.put("mobile", information.getString("mobile"));

                JSONObject paramJson = new JSONObject();
                paramJson.put("mobile", information.getString("mobile"));
                paramJson.put("updateBy", information.getInteger("userId"));
                sysUserService.updateSysLogin(paramJson);
//                Object myMenu = sysUserService.findMyMenu(information.getInteger("userId")).getData();
//                myMenu = null == myMenu ? Collections.emptyList() : myMenu;
//                resultMap.put("menu", myMenu);
//                 查询用户是否是首次登录
//                List<LoginHistory> loginHistoryList = loginHistoryService.getLoginHistoryByUserId(
//                        information.getInteger("userId")).getData();
//                if (null == loginHistoryList || loginHistoryList.isEmpty()) {
//                    resultMap.put("firstLogin", true);
//                } else {
//                    resultMap.put("firstLogin", false);
//                }
            }
        } catch (Exception e) {
            log.error("登录失败：", e);
            return R.fail(ResultCodeEnum.LOGIN_ERROR);
        }

        // 插入登录记录
//        insertLoginHistory(data.getJSONObject("additionalInformation"), requestParams.get(Constants.LOGIN_CLIENT_ID));

        return R.successDef(resultMap, "登录成功");
    }

    private void insertLoginHistory(JSONObject information, String clientId) {
        LoginHistory loginHistory = new LoginHistory();
        if (information.containsKey("userId")) {
            loginHistory.setUserId(information.getInteger("userId"));
            loginHistory.setUsername(information.getString("username"));
            loginHistory.setPhone(information.getString("phone"));
        }
        loginHistory.setClientId(clientId);
        loginHistory.setLoginIp(ThreadContext.get(Constants.THREAD_CONTEXT_IP));
        loginHistory.setLoginTime(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        loginHistoryService.insertNewLoginHistory(loginHistory);
    }
}
