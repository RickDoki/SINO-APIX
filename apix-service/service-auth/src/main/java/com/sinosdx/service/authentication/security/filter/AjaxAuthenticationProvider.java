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
package com.sinosdx.service.authentication.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.consumer.SysUserServiceFeign;
import com.sinosdx.service.authentication.dao.entity.BaseUser;
import com.sinosdx.service.authentication.exceptions.InvalidPasswordException;
import com.sinosdx.service.authentication.exceptions.UserBannedException;
import com.sinosdx.service.authentication.exceptions.UserNotRegisteredException;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author wendy
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(AjaxAuthenticationProvider.class);

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private SysUserServiceFeign sysUserService;

    //    @Resource
    //    private final RedisTemplate redisTemplate;

    //    @Autowired
    //    public AjaxAuthenticationProvider(
    //            final BCryptPasswordEncoder encoder, final RedisTemplate redisTemplate) {
    //        this.encoder = encoder;
    //        this.redisTemplate = redisTemplate;
    //    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Assert.notNull(authentication, "No authentication provided");
        String username = (String) authentication.getPrincipal();
        logger.info(String.format("login username: %s", username));
        String password = (String) authentication.getCredentials();
        LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) authentication.getDetails();
        String loginType = details.get("login_type");

        Object objectR = null;
        try {
            R<Object> userResult = sysUserService.userLogin(username);
            if (userResult.getCode() == ResultCodeEnum.USER_BANNED.getCode()) {
                throw new UserBannedException("用户已被禁用");
            }
            objectR = userResult.getData();
            if (null == objectR) {
                throw new UserNotRegisteredException("用户不存在，请先注册");
            }
        } catch (UserBannedException e) {
            logger.error("用户已被禁用", e);
            throw new UserBannedException("用户已被禁用");
        } catch (UserNotRegisteredException e) {
            logger.error("用户未注册", e);
            throw new UserNotRegisteredException("用户未注册");
        } catch (Exception e) {
            logger.error("获取登录用户失败", e);
            throw new UsernameNotFoundException("用户不存在或获取用户信息异常，请检查登录账户");
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(objectR);
        if (!loginType.equals(Constants.LOGIN_TYPE_QYWX) && !loginType.equals(Constants.LOGIN_TYPE_CSP)) {
            String userPwd = jsonObject.getString("password");
            if (!encoder.matches(password, userPwd)) {
                throw new InvalidPasswordException("登录失败，密码错误");
            }
        }

        BaseUser user = new BaseUser();
        user.setId(jsonObject.getInteger("id"));
        user.setUsername(jsonObject.getString("username"));
        user.setMobile(jsonObject.getString("mobile"));
        user.setAccount(jsonObject.getString("account"));
        user.setEmail(jsonObject.getString("email"));
        user.setRoles(Collections.singletonList("USER"));

        // TODO:根据不同的登录类型查询验证用户

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}