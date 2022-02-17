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
package com.sinosdx.service.authentication.security;

import com.sinosdx.service.authentication.dao.entity.BaseUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.*;

/**
 * @author wendy
 * @date 2020/11/27
 */
public class CommonUserConverter implements UserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Map<String,Object> tokenContent = new HashMap<>();

        BaseUser user = (BaseUser) userAuthentication.getPrincipal();
//        BaseUser user = principal.getUser();

        tokenContent.put("userId", user.getId());
        tokenContent.put("username", user.getUsername());
        tokenContent.put("mobile", user.getMobile());
        tokenContent.put("email", user.getEmail());
        tokenContent.put("account", user.getAccount());
        tokenContent.put("roles", user.getRoles());
        return tokenContent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        BaseUser user = new BaseUser();

        System.out.println(map);
        // 客户端登录逻辑
        if (!map.containsKey("username")) {
            user.setClientId((String) map.get("client_id"));
        }
        // 用户登录逻辑
        else {
            user.setId((Integer) map.get("userId"));
            user.setUsername((String) map.get("username"));
            user.setEmail((String) map.get("email"));
            user.setMobile((String) map.get("mobile"));
            user.setAccount((String) map.get("account"));

            List<String> roles = (List<String>) map.get("roles");
            roles.forEach(roleName -> user.getRoles().add(roleName));
        }

//        CommonUser principal = new CommonUser(user);

        return new UsernamePasswordAuthenticationToken(user, "n/a", new ArrayList<>());
    }
}
