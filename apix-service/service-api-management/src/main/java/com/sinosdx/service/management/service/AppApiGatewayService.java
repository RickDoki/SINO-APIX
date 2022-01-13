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
     * @param appClientCode
     */
    void updateApiGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode);

    /**
     * 删除已发布的api网关
     *
     * @param applicationId
     * @param apiList
     * @param appClientCode
     */
    void deleteApiGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode);
}
