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
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.service.TenantsService;
import com.sinosdx.service.user.service.dto.Tenant;
import com.sinosdx.service.user.service.dto.TenantsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenjian
 * @create 2021-06-25 13:36
 * @Description 已经迁移到代理网关服务，此处用不到
 */
@RestController
public class TenantsController {

    @Autowired
    TenantsService tenantsService;

    /**
     * dbaas从 csp1.0 和 2.0 获取租户信息
     * @return
     */
    @GetMapping(value = "/tenants/map")
    public HashMap<String, Tenant> getTenantMap(){

        return tenantsService.getTenants();
    }

    /**
     *  rancher用接口 获取用户组
     *  获取所有应用组信息
     * @param tenantId
     * @return
     */
    @GetMapping("/v1/tenants/{tenantId}/instance_group")
    public List<TenantsGroup> getTenantsGroup(@PathVariable("tenantId") String tenantId){
        return tenantsService.getTenantsGroup(tenantId);
    }


    /**
     * 获取当前登录的用户信息
     * @return
     * @RequestHeader(name = "JWT", required = true) String jwt
     */
    @GetMapping("/v1/user/info")
    public Map<String,Object>  getUserInfo(@RequestHeader Map<String, String> headers){
        String jwt;
        if(headers.containsKey("JWT")){
            jwt = headers.get("JWT");
        }else if(headers.containsKey("jwt")){
            jwt = headers.get("jwt");
        }else{
            jwt="";
        }
        return tenantsService.getUserInfo(jwt);
    }

    @GetMapping("/service/api/regionList")
    public JSONObject getRegionList(){

        return tenantsService.getRegionList();
    }

    /**
     * 查询租户列表接口（兼容c01和csp2.0）
     *
     * @param jwt
     * @return
     */
    @GetMapping("/user/allTenants")
    public R<Object> getAllTenants(@RequestHeader(name = "Authorization", required = true) String jwt) {
        jwt = jwt.substring(Constants.AUTH_HEADER_PREFIX.length());
        return tenantsService.getAllTenants(jwt);
    }

}
