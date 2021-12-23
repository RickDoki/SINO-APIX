package com.sinosdx.service.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.ShiroService;
import com.sinosdx.service.user.service.SysMenuService;
import com.sinosdx.service.user.service.SysRoleMenuService;
import com.sinosdx.service.user.service.SysUserRoleService;
import com.sinosdx.service.user.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 */
@RestController
@RequestMapping("/user/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 导航菜单
     */
    @GetMapping("/nav/{userId}")
    public R nav(@PathVariable("userId") Long userId) {
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(userId);
        Set<String> permissions = shiroService.getUserPermissions(userId);

        return R.success(new MapUtils().put("menuList", menuList).put("permissions", permissions));
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("sys:menu:list")
    public R list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

//        Map<Long, List<SysMenuEntity>> map = menuList.stream().collect(Collectors.groupingBy(SysMenuEntity::getParentId));
//        map.forEach((k,v)-> {
//            // k == 0 为 menu
//        });
//        List<Long> menuIds = menuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
//        List<SysMenuEntity> menuEntities = sysMenuService.getAllMenuList(menuIds);
        return R.success(menuList);
    }

    /**
     * 获取对应权限菜单列表
     */
    @GetMapping("/permList/{userId}")
//    @RequiresPermissions("sys:menu:list")
    public R permList(@PathVariable(value = "userId") Long userId) {
        List<Long> longs = sysUserRoleService.queryRoleIdList(userId);
        List<SysMenuEntity> menuList;
        if (longs.contains(SysConstant.SUPER_ROLE)) {
            menuList = sysMenuService.list();
        } else {
            Long last = CollectionUtil.getLast(longs);
            menuList = sysRoleMenuService.queryMenuList(last);
        }
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }


//        Map<Long, List<SysMenuEntity>> map = menuList.stream().collect(Collectors.groupingBy(SysMenuEntity::getParentId));
//        map.forEach((k,v)-> {
//            // k == 0 为 menu
//        });
//        List<Long> menuIds = menuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
//        List<SysMenuEntity> menuEntities = sysMenuService.getAllMenuList(menuIds);
        return R.success(menuList);
    }


    /**
     * 所有菜单列表
     */
    @GetMapping("/test")
//    @RequiresPermissions("sys:menu:list")
    public R test() {
//        List<SysMenuEntity> menuList = sysMenuService.list();
//        List<Long> menuIds = menuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
//        List<SysMenuEntity> userMenuList = sysMenuService.getAllMenuList(menuIds);
        return R.success();
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
//    @RequiresPermissions("sys:menu:select")
    public R select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return R.success(menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
//    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return R.success(menu);
    }

    /**
     * 保存
     */
    @AuditLog(type = "保存", name = "菜单")
    @PostMapping("/save")
//    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.success();
    }

    /**
     * 修改
     */
    @AuditLog(type = "修改", name = "菜单")
    @PostMapping("/update")
//    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenuEntity menu) {
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.success();
    }

    /**
     * 删除
     */
    @AuditLog(type = "删除", name = "菜单")
    @PostMapping("/delete/{menuId}")
//    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable("menuId") long menuId) {
//        if (menuId <= 31) {
//            return R.fail("系统菜单，不能删除");
//        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.fail("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.success();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
//            throw new RRException("菜单名称不能为空");
//            throw new UserRoleException(ResultCodeEnum.MENU_NAME_NOT_EXISTED);
            AssertsUtil.fail(ResultCodeEnum.MENU_NAME_NOT_EXISTED);
        }

        if (menu.getParentId() == null) {
//            throw new UserRoleException(ResultCodeEnum.MENU_PRE_NOT_EXISTED);
            AssertsUtil.fail(ResultCodeEnum.MENU_PRE_NOT_EXISTED);
        }

        //菜单
        if (menu.getType() == SysConstant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
//                throw new UserRoleException(ResultCodeEnum.MENU_URL_NOT_EXISTED);
                AssertsUtil.fail(ResultCodeEnum.MENU_URL_NOT_EXISTED);
            }
        }

        //上级菜单类型
        int parentType = SysConstant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == SysConstant.MenuType.CATALOG.getValue() ||
                menu.getType() == SysConstant.MenuType.MENU.getValue()) {
            if (parentType != SysConstant.MenuType.CATALOG.getValue()) {
//                throw new UserRoleException(ResultCodeEnum.MENU_PARENT_ONLY_CATALOG);
                AssertsUtil.fail(ResultCodeEnum.MENU_PARENT_ONLY_CATALOG);
            }
            return;
        }

        //按钮
        if (menu.getType() == SysConstant.MenuType.BUTTON.getValue()) {
            if (parentType != SysConstant.MenuType.MENU.getValue()) {
//                throw new UserRoleException(ResultCodeEnum.MENU_PARENT_ONLY_MENU);
                AssertsUtil.fail(ResultCodeEnum.MENU_PARENT_ONLY_MENU);
            }
            //TODO 菜单名称重名校验

            return;
        }
    }
}
