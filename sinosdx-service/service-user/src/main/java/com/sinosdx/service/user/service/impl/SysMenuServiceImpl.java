package com.sinosdx.service.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.mapper.SysMenuDao;
import com.sinosdx.service.user.service.SysMenuService;
import com.sinosdx.service.user.service.SysRoleMenuService;
import com.sinosdx.service.user.service.SysUserRoleService;
import com.sinosdx.service.user.service.SysUserService;
import com.sinosdx.service.user.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
//        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        List<Long> roleIdList = sysUserRoleService.queryRoleIdListWithOrgId(userId);
        //系统管理员，拥有最高权限
        if (roleIdList.contains(SysConstant.SUPER_ROLE)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联

        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    @Override
    public List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == SysConstant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            // 菜单
            if (entity.getType() == SysConstant.MenuType.MENU.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
