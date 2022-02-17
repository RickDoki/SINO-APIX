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
package com.sinosdx.service.user.config;

/**
 * 认证
 */
//@Component
//public class OAuth2Realm extends AuthorizingRealm {
public class OAuth2Realm {
//    @Autowired
//    private ShiroService shiroService;
//
////    @Override
////    public boolean supports(AuthenticationToken token) {
////        return token instanceof OAuth2Token;
////    }
//
//    /**
//     * 授权(验证权限时调用)
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        SysUser user = (SysUser) principals.getPrimaryPrincipal();
////        Long userId = user.getUserId();
//        Long userId = 1L;
//
//        //用户权限列表
//        Set<String> permsSet = shiroService.getUserPermissions(userId);
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(permsSet);
//        return info;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        return null;
//    }

    /**
     * 认证(登录时调用)
     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String accessToken = (String) token.getPrincipal();
//        //查询用户信息
//        SysUser user = shiroService.queryUser(1L);
//        //账号锁定
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
//        return info;
//    }
}
