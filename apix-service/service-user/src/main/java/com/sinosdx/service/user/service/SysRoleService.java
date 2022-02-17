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
