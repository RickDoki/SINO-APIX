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
package com.sinosdx.service.log.service;

import com.sinosdx.common.base.base.service.SuperService;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.AnalysisGatewayLog;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import com.sinosdx.service.log.service.dto.AppRequestNumDTO;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
public interface IAnalysisGatewayLogService extends SuperService<AnalysisGatewayLog> {

//    /**
//     * 查询网关日志
//     *
//     *
//     * @param httpMethod
//     * @param domain
//     * @param requestPath
//     * @param startTime
//     * @param endTime
//     * @param limit
//     * @param offset
//     * @return
//     */
//    R<Object> queryGatewayLogList(String httpMethod, String domain, String requestPath, Long startTime, Long endTime, Integer limit, Integer offset);


    R<Object> queryGatewayByAppCode(List<String> appCodes, Long startDate, Long endDate);
    R<Object> queryGatewayLogByStatus(String appCode,String requestUri, Long startDate, Long endDate);

}
