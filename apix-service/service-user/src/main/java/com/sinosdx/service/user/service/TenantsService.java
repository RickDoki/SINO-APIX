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
import com.sinosdx.service.user.service.dto.Tenant;
import com.sinosdx.service.user.service.dto.TenantsGroup;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenjian
 * @create 2021-06-25 10:04
 * @Description
 */
public interface TenantsService {

    HashMap<String, Tenant> getTenants();

    List<TenantsGroup> getTenantsGroup(String tenantId);

    Map<String, Object> getUserInfo(String jwt);

    JSONObject getRegionList();

    R<Object> getAllTenants(String jwt);

}
