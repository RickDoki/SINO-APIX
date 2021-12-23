package com.sinosdx.service.user.service;

import com.sinosdx.common.base.result.R;

/**
 * @author wendy
 * @date 2021/6/17
 */
public interface SocService {

    /**
     * 获取区域列表
     *
     * @param region
     * @param serviceKey
     * @return
     */
    R<Object> getRegionList(String region, String serviceKey);

    /**
     * 获取soc租户列表
     *
     * @param shortName
     * @return
     */
    R<Object> getSocTenantList(String shortName);
}
