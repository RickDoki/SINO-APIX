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
import com.sinosdx.service.user.dao.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhuangyang
 * @date 2020-11-23 16:06
 * @description
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser queryByMobile(@Param("mobile") String mobile);

    List<Map<String, Object>> queryUserLoginInfo(@Param("mobile") String mobile);

    List<Object> queryUserByCondition(@Param("username") String username,
                                      @Param("roleId") Integer roleId,
                                      @Param("limit") Integer limit,
                                      @Param("offset") Integer offset,
                                      @Param("userIdList") List<Integer> userIdList);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询所有用户id
     *
     * @return
     */
    List<Integer> queryAllUserIdList();

    SysUser queryByUserId(@Param("userId") Integer userId);

    SysUser queryByClientId(@Param("sysClientId") Integer sysClientId);
}
