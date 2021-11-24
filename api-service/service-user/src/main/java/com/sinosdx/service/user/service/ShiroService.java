package com.sinosdx.service.user.service;

import com.sinosdx.service.user.dao.entity.SysUser;

import java.util.Set;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);


    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(Long userId);
}
