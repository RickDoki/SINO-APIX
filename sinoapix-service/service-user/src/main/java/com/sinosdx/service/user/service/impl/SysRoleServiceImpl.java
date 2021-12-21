

package com.sinosdx.service.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.user.constants.SysConstant;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.entity.SysRoleEntity;
import com.sinosdx.service.user.dao.entity.SysRoleOrgEntity;
import com.sinosdx.service.user.dao.entity.SysUserOrgEntity;
import com.sinosdx.service.user.dao.mapper.SysRoleDao;
import com.sinosdx.service.user.dao.mapper.SysUserMapper;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.*;
import com.sinosdx.service.user.utils.PageUtils;
import com.sinosdx.service.user.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色
 */
@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleOrgService sysRoleOrgService;

    @Autowired
    private SysUserOrgService sysUserOrgService;

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long createUserId = null;
        String roleName = null;
        if (params.containsKey("roleName")) {
            roleName = (String) params.get("roleName");
        }

        if (params.containsKey("createUserId")) {
            createUserId = Long.parseLong(params.get("createUserId") + "");
        }
        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(params),
                new LambdaQueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), SysRoleEntity::getRoleName, roleName)
                        .eq(createUserId != null, SysRoleEntity::getCreateUserId, createUserId)
                        .orderByDesc(SysRoleEntity::getCreateTime)
        );
        PageUtils pageUtils = new PageUtils(page);
        List<SysRoleEntity> list = (List<SysRoleEntity>) pageUtils.getList();
        list.forEach(a -> {
            Long roleId = a.getRoleId();
            List<SysMenuEntity> menuList = sysRoleMenuService.queryMenuList(roleId);
            List<Long> menuIds = menuList.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toList());
            List<SysMenuEntity> menuEntities = sysMenuService.getAllMenuList(menuIds);
            a.setMenuList(menuEntities);
            a.setMenuIdList(menuIds);
        });
        pageUtils.setList(list);
        return pageUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        //检查权限是否越权
        checkPrems(role);

        List<SysUserOrgEntity> list = sysUserOrgService.list(
                new LambdaQueryWrapper<SysUserOrgEntity>()
                        .eq(SysUserOrgEntity::getUserId, role.getCreateUserId())
                        .orderByDesc(SysUserOrgEntity::getId)
        );
        if (CollectionUtil.isNotEmpty(list)) {
            // 取最后一个 组织的orgId
            SysUserOrgEntity last = CollectionUtil.getLast(list);
            Long orgId = last.getOrgId();
            List<String> sysRoleNames = cheakRoleName(last).stream().map(SysRoleEntity::getRoleName).collect(Collectors.toList());
            // 校验是否存在同名角色
            if (sysRoleNames.contains(role.getRoleName())) {
//                throw new UserRoleException(ResultCodeEnum.ROLE_IS_EXIST);
                AssertsUtil.fail(ResultCodeEnum.ROLE_IS_EXIST);
            }
            //  保存Org 和 Role 的关联
            role.setCreateTime(new Date());
            role.setUpdateTime(new Date());
            if (role.getParentId() == null) {
                List<Long> longs = sysUserRoleService.queryRoleIdListWithOrgId(role.getCreateUserId());
                role.setParentId(CollectionUtil.getLast(longs));
            }
            this.save(role);
            //保存角色与菜单关系
            sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
            sysRoleOrgService.save(new SysRoleOrgEntity().setRoleId(role.getRoleId()).setOrgId(orgId));
        } else {
            // 没有关联的组织
//            throw new UserRoleException(ResultCodeEnum.ROLE_NO_ORG);
            AssertsUtil.fail(ResultCodeEnum.ROLE_NO_ORG);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {

        //检查权限是否越权
        checkPrems(role);

        List<SysUserOrgEntity> list = sysUserOrgService.list(
                new LambdaQueryWrapper<SysUserOrgEntity>()
                        .eq(SysUserOrgEntity::getUserId, role.getCreateUserId())
                        .orderByDesc(SysUserOrgEntity::getId)
        );
        if (CollectionUtil.isNotEmpty(list)) {
            // 取最后一个 组织的orgId
            SysUserOrgEntity last = CollectionUtil.getLast(list);
            List<String> sysRoleNames = cheakRoleName(last).stream()
                    .filter(e -> !role.getRoleId().equals(e.getRoleId()))
                    .map(SysRoleEntity::getRoleName).collect(Collectors.toList());
            // 校验是否存在同名角色
            if (sysRoleNames.contains(role.getRoleName())) {
//                throw new UserRoleException(ResultCodeEnum.ROLE_IS_EXIST);
                AssertsUtil.fail(ResultCodeEnum.ROLE_IS_EXIST);
            }
            //更新角色与菜单关系
            sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
        }

        role.setUpdateTime(new Date());
        this.updateById(role);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);

        //删除 Org 和 Role 的关联
        sysRoleOrgService.deleteByRoleIds(roleIds);

    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleEntity role) {
        log.info("checkPrems ==> {}", JSON.toJSONString(role));
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
//        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(role.getCreateUserId());
        List<Long> roleIdList = sysUserRoleService.queryRoleIdListWithOrgId(role.getCreateUserId());
        if (roleIdList.contains(SysConstant.SUPER_ROLE)) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
        List<Long> roleMenuIdList = role.getMenuIdList().stream()
                .filter(e -> !"-666666".equals(e.toString())).collect(Collectors.toList());
        log.info("menuIdList {}", menuIdList);
        log.info("roleMenuIdList {}", roleMenuIdList);
        log.info("containsAll {}", !menuIdList.containsAll(roleMenuIdList));
        //判断是否越权
        if (!menuIdList.containsAll(roleMenuIdList)) {
//            throw new RRException("新增角色的权限，已超出你的权限范围");
//            throw new UserRoleException(ResultCodeEnum.ROLE_NO_PREM);
            AssertsUtil.fail(ResultCodeEnum.ROLE_NO_PREM);
        }
    }

//    public static void main(String[] args) {
//        List<Long> menuIdList = Arrays.asList(70L,71L,72L,73L ,90L ,91L ,92L ,93L ,94L ,95L);
//        List<Long> roleMenuIdList = Arrays.asList(71L, 72L, -666666L, 70L).stream()
//                .filter(e-> !"-666666".equals(e.toString())).collect(Collectors.toList());
//        System.out.println("aaaaa==>"+roleMenuIdList.toString());
//        System.out.println("aaaaa==>" + menuIdList.containsAll(roleMenuIdList));
//    }

    /**
     * 查询是否重名角色
     *
     * @param userOrgEntity
     */
    private List<SysRoleEntity> cheakRoleName(SysUserOrgEntity userOrgEntity) {
        Long orgId = userOrgEntity.getOrgId();
        LambdaQueryWrapper<SysRoleOrgEntity> wrapper = new LambdaQueryWrapper<SysRoleOrgEntity>()
                .eq(SysRoleOrgEntity::getOrgId, orgId);
        List<SysRoleOrgEntity> sysRoleOrgEntities = sysRoleOrgService.list(wrapper);
        List<Long> roleIds = sysRoleOrgEntities.stream().map(SysRoleOrgEntity::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<SysRoleEntity> sysRoleWrapper = new LambdaQueryWrapper<>();
        sysRoleWrapper.in(SysRoleEntity::getRoleId, roleIds);
        return this.list(sysRoleWrapper);
    }


    @Override
    public List<SysRoleEntity> queryListParentId(Long parentId, List<Long> roleIdList) {
        List<SysRoleEntity> roleList = queryListParentId(parentId);
        if (roleIdList == null) {
            return roleList;
        }

        List<SysRoleEntity> userRoleList = new ArrayList<>();
        for (SysRoleEntity role : roleList) {
            if (roleIdList.contains(role.getRoleId())) {
                userRoleList.add(role);
            }
        }
        return userRoleList;
    }

    @Override
    public List<SysRoleEntity> getAllRoleList(Long parnetId, List<Long> roleIdList) {
        //查询根菜单列表
        List<SysRoleEntity> roleList = queryListParentId(parnetId, roleIdList);
        //递归获取子菜单
        getRoleTreeList(roleList, roleIdList);

        return roleList;
    }


    /**
     * 递归
     */
    private List<SysRoleEntity> getRoleTreeList(List<SysRoleEntity> roleList, List<Long> roleIdList) {
        List<SysRoleEntity> subRoleList = new ArrayList<>();

        for (SysRoleEntity entity : roleList) {
            entity.setList(getRoleTreeList(queryListParentId(entity.getRoleId(), roleIdList), roleIdList));
            subRoleList.add(entity);
        }

        return subRoleList;
    }

    @Override
    public List<SysRoleEntity> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<Long> queryRoleIdByUserId(Long userId) {
        return baseMapper.queryRoleIdByUserId(userId);
    }

    @Override
    public List<Long> queryRoleIds(Long roleId, Long userId, List<Long> roleIdList) {
//        List<Long> roleIdList = Lists.newArrayList();
        roleIdList.add(roleId);
        List<SysRoleEntity> sysRoleEntities = baseMapper.queryListParentIdWithOrgId(userId, roleId);
        sysRoleEntities.forEach(e -> {
            Long id = e.getRoleId();
//            roleIdList.add(id);
            queryRoleIds(id, userId, roleIdList);
        });
        return roleIdList;
    }

    /**
     * 查询所有角色等级小于等于当前用户的用户id集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> queryAllUserIdListByRole(Integer userId) {
        SysRoleEntity role = sysRoleDao.queryRoleByUserId(userId);
        // 如果用户是超级管理员，返回所有用户id
        if (SysConstant.SUPER_ROLE.equals(role.getRoleId())) {
            return sysUserMapper.queryAllUserIdList();
        }
        return sysRoleDao.queryAllUserIdListByRole(userId, role.getParentId().intValue());
    }
}
