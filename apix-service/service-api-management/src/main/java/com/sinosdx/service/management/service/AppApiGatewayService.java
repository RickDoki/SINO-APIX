package com.sinosdx.service.management.service;

import com.sinosdx.service.management.dao.entity.Api;

import java.util.List;

/**
 * @author wendy
 * @date 2022/1/12
 */
public interface AppApiGatewayService {

    /**
     * api发布到网关
     *
     * @param applicationId
     * @param apiList
     */
    void updateGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode);
}
