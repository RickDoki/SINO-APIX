package com.sinosdx.service.log.service;

import com.sinosdx.common.base.base.service.SuperService;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.GatewayLog;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
public interface IGatewayLogService extends SuperService<GatewayLog> {

    /**
     * 查询网关日志
     *
     *
     * @param httpMethod
     * @param domain
     * @param requestPath
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryGatewayLogList(String httpMethod, String domain, String requestPath, Long startTime, Long endTime,String appCode,Integer statusCode,Integer limit, Integer offset);


    R<Object> queryGatewayByAppCode(List<String> appCodes, Long startDate, Long endDate);

}
