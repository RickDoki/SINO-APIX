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
