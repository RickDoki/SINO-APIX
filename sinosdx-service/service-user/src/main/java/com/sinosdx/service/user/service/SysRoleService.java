

package com.sinosdx.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.entity.SysRoleEntity;
import com.sinosdx.service.user.utils.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);


    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    List<SysRoleEntity> getAllRoleList(Long parnetId,List<Long> roleIdList);

    List<SysRoleEntity> queryListParentId(Long parentId, List<Long> roleIdList);

    List<SysRoleEntity> queryListParentId(Long parentId);

    List<Long> queryRoleIdByUserId(Long userId);

    List<Long> queryRoleIds(Long roleId,Long userId,List<Long> roleIdList);

    /**
     * 查询所有角色等级小于等于当前用户的用户id集合
     *
     * @param userId
     * @return
     */
    List<Integer> queryAllUserIdListByRole(Integer userId);

}
