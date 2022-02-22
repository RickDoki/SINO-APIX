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
