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
package com.sinosdx.service.management.consumer;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.dao.entity.SysClient;
import com.sinosdx.service.management.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/3
 */
@FeignClient("service-user")
public interface SysUserServiceFeign {

    /**
     * 根据用户ID查询用户实体对象SysUser
     *
     * @param id
     * @return
     */
    @GetMapping("/user/sys/{id}")
    R<Map<String, Object>> findUserById(@PathVariable("id") Integer id);

    /**
     * 根据角色类型查询用户信息
     *
     * @param roleType
     * @return
     */
    @GetMapping("/users/role-type/{roleType}/users")
    R<List<Object>> selectUsersByRoleType(@PathVariable("roleType") Integer roleType);

    /**
     * 根据姓名和手机号查询用户
     *
     * @param username
     * @param phone
     * @return
     */
    @GetMapping("/user/sys/phone-username")
    R<Object> selectUserByPhoneAndUsername(@RequestParam(value = "username", required = false) String username,
                                           @RequestParam("phone") String phone);

    /**
     * 查询所有角色等级小于等于当前用户的用户id集合
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/sys/role/userId/list")
    List<Integer> queryAllUserIdListByRole(@RequestParam("userId") Integer userId);

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    @GetMapping("/user/sys/client")
    R<SysClient> queryClientByUserId(@RequestParam("sysUserId") Integer sysUserId);

    /**
     * 根据clientId查询user
     *
     * @param sysClientId
     * @return
     */
    @GetMapping("/user/sys")
    R<JSONObject> queryUserByClientId(@RequestParam Integer sysClientId);
}
