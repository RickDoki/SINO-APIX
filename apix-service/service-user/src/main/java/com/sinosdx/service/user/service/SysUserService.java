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

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysUser;
import com.sinosdx.service.user.service.dto.SysRegisterDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Wendy
 * @date 2021/6/18
 */
public interface SysUserService {

    /**
     * 用户注册
     *
     * @param sysRegisterDTO
     * @return
     */
    R<Object> userRegister(SysRegisterDTO sysRegisterDTO);

    /**
     * 查询用户登录信息
     *
     * @param mobile
     * @return
     */
    R<Object> queryUserLoginInfo(String mobile);

    /**
     * 条件查询用户列表
     *
     * @param username
     * @param roleId
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryUserListByCondition(String username, Integer roleId, Integer limit, Integer offset);

    /**
     * 根据用户id查询用户实体对象SysUser
     *
     * @param id
     * @return
     */
    R<Object> findUserById(Integer id);

    /**
     * 更新用户
     *
     * @param sysUser
     * @return
     */
    R<Object> updateUser(SysUser sysUser);

    /**
     * 根据姓名和手机号查询用户
     *
     * @param username
     * @param phone
     * @return
     */
    R<Object> selectUserByPhoneAndUsername(String username, String phone);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 通过csp营销门户登录的用户获取中台token
     *
     * @param mobile
     * @return
     */
    R<Object> cspUserLogin(String mobile);

    /**
     * 更新用户登录时间
     *
     * @param mobile
     * @param updateBy
     * @return
     */
    R<Object> updateSysLogin(String mobile, Integer updateBy);

    /**
     * 管理员添加用户
     *
     * @param sysUser
     * @return
     */
    R<Object> addUserByManager(SysUser sysUser);

    /**
     * 用户修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    R<Object> modifyUserPassword(String oldPwd, String newPwd);
}
