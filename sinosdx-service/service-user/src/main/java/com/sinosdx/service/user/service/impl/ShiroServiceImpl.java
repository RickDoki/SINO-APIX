package com.sinosdx.service.user.service.impl;

import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.entity.SysUser;
import com.sinosdx.service.user.dao.mapper.SysMenuDao;
import com.sinosdx.service.user.dao.mapper.SysUserMapper;
import com.sinosdx.service.user.dao.mapper.SysUserRoleDao;
import com.sinosdx.service.user.service.ShiroService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserMapper sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;


    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
//        List<Long> roleIdList = sysUserRoleDao.queryRoleIdList(userId);
            List<Long> roleIdList = sysUserRoleDao.queryRoleIdListWithOrgId(userId);
        //系统管理员，拥有最高权限
        if (roleIdList.contains(SysConstant.SUPER_ROLE)) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

//    @Override
//    public SysUserTokenEntity queryByToken(String token) {
//        return sysUserTokenDao.queryByToken(token);
//    }
//
    @Override
    public SysUser queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
