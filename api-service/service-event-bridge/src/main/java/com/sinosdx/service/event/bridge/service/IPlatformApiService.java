package com.sinosdx.service.event.bridge.service;


import com.sinosdx.service.event.bridge.service.bo.PlatformApiBO;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2021-01-17 01:10
 * @description
 */
public interface IPlatformApiService {

    /**
     * 获取api列表
     *
     * @param serviceName
     * @return
     */
    List<PlatformApiBO> getApiList(String serviceName);
}
