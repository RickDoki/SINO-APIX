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
package com.sinosdx.service.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysUser;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.SysUserService;
import com.sinosdx.service.user.service.dto.SysRegisterDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2021/6/22
 */
@RestController
@RequestMapping("/user/sys")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户注册
     *
     * @param sysRegisterDTO
     * @return
     */
    @AuditLog(type = "用户注册", name = "用户")
    @PostMapping("/register")
    public R<Object> userRegister(@RequestBody SysRegisterDTO sysRegisterDTO) {
        return sysUserService.userRegister(sysRegisterDTO);
    }

    /**
     * 查询用户登录信息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/login-info")
    public R<Object> queryUserLoginInfo(@RequestParam("mobile") String mobile) {
        return sysUserService.queryUserLoginInfo(mobile);
    }

    /**
     * 用户条件查询列表
     *
     * @param username
     * @param roleId
     * @param limit
     * @param offset
     * @return
     */
    @AuditLog(type = "查询列表", name = "用户")
    @GetMapping("/list")
    public R<Object> queryUserListByCondition(@RequestParam(value = "username", required = false) String username,
                                              @RequestParam(value = "roleId", required = false) Integer roleId,
                                              @RequestParam(value = "limit", required = false) Integer limit,
                                              @RequestParam(value = "offset", required = false) Integer offset) {
        return sysUserService.queryUserListByCondition(username, roleId, limit, offset);
    }

    /**
     * 根据用户id查询用户实体对象SysUser
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Object> findUserById(@PathVariable Integer id) {
        return sysUserService.findUserById(id);
    }

    /**
     * 更新用户
     *
     * @param userId
     * @param sysUser
     * @return
     */
    @AuditLog(type = "更新", name = "用户")
    @PutMapping("/{userId}")
    public R<Object> updateUser(@PathVariable("userId") Integer userId,
                                @RequestBody SysUser sysUser) {
        sysUser.setId(userId);
        return sysUserService.updateUser(sysUser);
    }

    /**
     * 根据姓名和手机号查询用户
     *
     * @param username
     * @param phone
     * @return
     */
    @GetMapping("/phone-username")
    public R<Object> selectUserByPhoneAndUsername(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam("phone") String phone) {
        return sysUserService.selectUserByPhoneAndUsername(username, phone);
    }

    @PostMapping("/testPost")
    public R<Object> testPost() {
        return R.success();
    }

    @AuditLog(type = "查询", name = "测试")
    @GetMapping("/testGet")
    public R<Object> testGet() {
        System.out.println("get");
        return R.success("get");
    }

    @DeleteMapping("/test/{id}/delete")
    public R<Object> testDelete(@PathVariable("id") Integer id, @RequestParam(value = "name", required = false) String name,
                                @RequestBody JSONObject jsonObject) {
        System.out.println("id " + id + " name " + name);
        System.out.println(jsonObject);
        return R.success("delete");
    }

    /**
     * csp免密登录
     *
     * @param paramJson
     * @return
     */
    @AuditLog(type = "用户登录", name = "用户")
    @PostMapping("/login/csp")
    public R<Object> cspUserLogin(@RequestBody JSONObject paramJson) {
        if (StringUtils.isEmpty(paramJson.getString("mobile"))) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        return sysUserService.cspUserLogin(paramJson.getString("mobile"));
    }

    /**
     * 更新用户登录时间
     *
     * @param paramJson
     * @return
     */
    @AuditLog(type = "用户登录", name = "用户")
    @PutMapping("/login")
    public R<Object> updateSysLogin(@RequestBody JSONObject paramJson) {
        return sysUserService.updateSysLogin(paramJson.getString("mobile"), paramJson.getInteger("updateBy"));
    }

    /**
     * 管理员添加用户
     *
     * @param sysUser
     * @return
     */
    @AuditLog(type = "管理员添加用户", name = "用户")
    @PostMapping("/add")
    public R<Object> addUserByManager(@RequestBody SysUser sysUser) {
        return sysUserService.addUserByManager(sysUser);
    }

    /**
     * 用户修改密码
     *
     * @param paramJson
     * @return
     */
    @AuditLog(type = "修改密码", name = "用户")
    @PutMapping("/modify/pwd")
    public R<Object> modifyUserPassword(@RequestBody JSONObject paramJson) {
        if (StringUtils.isAnyEmpty(paramJson.getString("oldPwd"), paramJson.getString("newPwd"))) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        return sysUserService.modifyUserPassword(paramJson.getString("oldPwd"), paramJson.getString("newPwd"));
    }
}
