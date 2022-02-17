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
package com.sinosdx.service.log.controller;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.base.controller.SuperController;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import com.sinosdx.service.log.dao.entity.AnalysisGatewayLog;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import com.sinosdx.service.log.service.IAnalysisGatewayLogService;
import com.sinosdx.service.log.service.IGatewayLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gateway/log")
@Api("gateway日志")
public class GatewayLogController extends
        SuperController<IGatewayLogService, Integer, GatewayLog, GatewayLog, GatewayLog, GatewayLog> {

    @Autowired
    private IGatewayLogService gatewayLogService;

    @Autowired
    private IAnalysisGatewayLogService analysisGatewayLogService;

    /**
     * 查询网关日志
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
    @GetMapping()
    public R<Object> queryGatewayLogList(@RequestParam(name = "httpMethod", required = false) String httpMethod,
                                         @RequestParam(name = "domain", required = false) String domain,
                                         @RequestParam(name = "requestPath", required = false) String requestPath,
                                         @RequestParam(name = "startTime", required = false) Long startTime,
                                         @RequestParam(name = "endTime", required = false) Long endTime,
                                         @RequestParam(name = "appCode", required = false) String appCode,
                                         @RequestParam(name = "statusCode", required = false) Integer statusCode,
                                         @RequestParam(name = "limit", required = false) Integer limit,
                                         @RequestParam(name = "offset", required = false)Integer offset) {
        return gatewayLogService.queryGatewayLogList(httpMethod, domain, requestPath, startTime, endTime,appCode,statusCode, limit, offset);
    }

    @GetMapping("/queryListByAppCode")
    public R<Object> queryListByAppCode(@RequestParam(name = "appCodes", required = false) List<String> appCodes,
                                         @RequestParam(name = "startTime", required = false) Long startTime,
                                         @RequestParam(name = "endTime", required = false) Long endTime){
        return analysisGatewayLogService.queryGatewayByAppCode(appCodes, startTime, endTime);
    }


    @GetMapping("/queryGatewayLogByStatus")
    public R<Object> queryGatewayLogByStatus(@RequestParam(name = "appCode", required = false) String appCode,
                                             @RequestParam(name = "requestUri", required = false) String requestUri,
                                        @RequestParam(name = "startTime", required = false) Long startTime,
                                        @RequestParam(name = "endTime", required = false) Long endTime){
        return analysisGatewayLogService.queryGatewayLogByStatus(appCode,requestUri,startTime, endTime);
    }

    @PostMapping(value = "/analysis",consumes = "text/html;charset=UTF-8")
    public R<Object> analysisGatewayLogSave(@RequestBody String log){
        GatewayLogDTO gatewayLogDTO = JSON.parseObject(log).toJavaObject(GatewayLogDTO.class);
        AnalysisGatewayLog analysisGatewayLog = new AnalysisGatewayLog();
        BeanUtils.copyProperties(gatewayLogDTO,analysisGatewayLog);
        boolean save = analysisGatewayLogService.save(analysisGatewayLog);
        return R.success(save);
    }


}
