package com.sinosdx.service.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.entity.SysRoleEntity;
import com.sinosdx.service.user.service.SysMenuService;
import com.sinosdx.service.user.service.SysRoleMenuService;
import com.sinosdx.service.user.service.SysRoleService;
import com.sinosdx.service.user.service.SysUserRoleService;
import com.sinosdx.service.user.service.dto.DeleteDto;
import com.sinosdx.service.user.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/user/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 角色列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if(params.containsKey("userId")){
//            List<Long> roleIds = sysUserRoleService.queryRoleIdList(Long.parseLong(params.get("userId") + ""));
            List<Long> roleIds = sysUserRoleService.queryRoleIdListWithOrgId(Long.parseLong(params.get("userId") + ""));
            if (!roleIds.contains(SysConstant.SUPER_ROLE)) {
                params.put("createUserId", params.get("userId"));
            }
        }
        PageUtils page = sysRoleService.queryPage(params);

        return R.success(page);
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
//    @RequiresPermissions("sys:role:select")
    public R select(@RequestParam(value = "userId",required = false) Long userId) {
        Map<String, Object> map = new HashMap<>();
        if(null != userId ){
//            List<Long> roleIds = sysUserRoleService.queryRoleIdList(userId);
            List<Long> roleIds = sysUserRoleService.queryRoleIdListWithOrgId(userId);
            //如果不是超级管理员，则只查询自己所拥有的角色列表
            if (!roleIds.contains(SysConstant.SUPER_ROLE)) {
                map.put("create_user_id", userId);
            }
        }
        List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
        list.forEach(a -> {
            Long roleId = a.getRoleId();
            List<SysMenuEntity> menuList = sysRoleMenuService.queryMenuList(roleId);
            List<Long> menuIds = menuList.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toList());
            List<SysMenuEntity> menuEntities = sysMenuService.getAllMenuList(menuIds);
            a.setMenuList(menuEntities);
            a.setMenuIdList(menuIds);
        });
        return R.success(list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
//    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<SysMenuEntity> menuList = sysRoleMenuService.queryMenuList(roleId);
        List<Long> menuIds = menuList.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toList());
        List<SysMenuEntity> menuEntities = sysMenuService.getAllMenuList(menuIds);
        role.setMenuList(menuEntities);
        role.setMenuIdList(sysRoleMenuService.queryMenuIdList(roleId));
        return R.success(role);
    }

    /**
     * 保存角色
     */
    @AuditLog(type = "保存", name = "角色")
    @PostMapping("/save")
//    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
//		ValidatorUtils.validateEntity(role);

//        role.setCreateUserId(role.getCreateUserId());
        sysRoleService.saveRole(role);
        return R.success();
    }

    /**
     * 修改角色
     */
    @AuditLog(type = "修改", name = "角色")
    @PostMapping("/update")
//    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
//		ValidatorUtils.validateEntity(role);
//        role.setCreateUserId(role.get);
        sysRoleService.update(role);
        return R.success();
    }

    /**
     * 删除角色
     */
    @AuditLog(type = "删除", name = "角色")
    @PostMapping("/delete")
//    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody DeleteDto deleteDto) {
        Long[] arr = new Long[deleteDto.getRoleIds().size()];
        deleteDto.getRoleIds().toArray(arr);
        sysRoleService.deleteBatch(arr);
        return R.success();
    }


    /**
     * 角色信息
     */
    @GetMapping("/roleTree")
//    @RequiresPermissions("sys:role:info")
    public R getRoleTree(@RequestParam(value = "userId",required = false) Long userId,
                         @RequestParam(value = "orgId",required = false) Long orgId,
                         @RequestParam(value = "roleId", required = false) Long roleId) {
        // 查询所有所有关联的权限的id
        List<Long> roleIdList = sysRoleService.queryRoleIdByUserId(userId);
        roleIdList.add(roleId);
        List<SysRoleEntity> allRoleList = sysRoleService.getAllRoleList(roleId, roleIdList);
        return R.success(allRoleList);
    }

    @GetMapping("/getRoleIds")
//    @RequiresPermissions("sys:role:info")
    public R getRoleIds(@RequestParam(value = "userId",required = false) Long userId) {
        List<List<Long>> roleIdListList = Lists.newArrayList();
//        List<Long> longs = sysUserRoleService.queryRoleIdList(userId);
        List<Long> longs = sysUserRoleService.queryRoleIdListWithOrgId(userId);
        if(longs.contains(SysConstant.SUPER_ROLE)){
            // 是超级管理员
            List<SysRoleEntity> list = sysRoleService.list(new LambdaQueryWrapper<>());
            List<Long> collect = list.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
            roleIdListList.add(collect);
        }else {
            // 查询所有关联的权限的id
            List<Long> idList = sysUserRoleService.queryRoleIdListWithOrgId(userId);

            idList.forEach(roleId->{
                List<Long> roleIdList = Lists.newArrayList();
                List<Long> roleIds = sysRoleService.queryRoleIds(roleId,userId,roleIdList);
                roleIdListList.add(roleIds);
            });
        }

        return R.success(roleIdListList);
    }

    /**
     * 查询所有角色等级小于等于当前用户的用户id集合
     *
     * @param userId
     * @return
     */
    @GetMapping("/userId/list")
    public List<Integer> queryAllUserIdListByRole(@RequestParam Integer userId) {
        return sysRoleService.queryAllUserIdListByRole(userId);
    }
}
