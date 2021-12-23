

package com.sinosdx.service.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.user.dao.entity.SysMenuEntity;
import com.sinosdx.service.user.dao.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    List<SysRoleEntity> queryListParentId(Long parentId);

    List<SysRoleEntity> queryListParentIdWithOrgId(@Param("userId") Long userId,@Param("parentId") Long parentId);

    List<Long> queryRoleIdByUserId(Long userId);

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    SysRoleEntity queryRoleByUserId(@Param("userId") Integer userId);

    /**
     * 查询所有角色等级小于等于当前用户的用户id集合
     *
     * @param userId
     * @param roleParentId
     * @return
     */
    List<Integer> queryAllUserIdListByRole(@Param("userId") Integer userId, @Param("roleParentId") Integer roleParentId);
}
