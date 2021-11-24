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
