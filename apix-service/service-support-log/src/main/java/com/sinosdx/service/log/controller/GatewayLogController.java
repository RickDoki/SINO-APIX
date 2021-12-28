package com.sinosdx.service.log.controller;

import com.sinosdx.common.base.base.controller.SuperController;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import com.sinosdx.service.log.service.IAnalysisGatewayLogService;
import com.sinosdx.service.log.service.IGatewayLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                         @RequestParam(name = "limit", required = false) Integer limit,
                                         @RequestParam(name = "offset", required = false)Integer offset) {
        return gatewayLogService.queryGatewayLogList(httpMethod, domain, requestPath, startTime, endTime, limit, offset);
    }

    @GetMapping("/queryListByAppCode")
    public R<Object> queryListByAppCode(@RequestParam(name = "appCodes", required = false) List<String> appCodes,
                                         @RequestParam(name = "startTime", required = false) Long startTime,
                                         @RequestParam(name = "endTime", required = false) Long endTime){
        return analysisGatewayLogService.queryGatewayByAppCode(appCodes, startTime, endTime);
    }


    @GetMapping("/queryGatewayLogByStatus")
    public R<Object> queryGatewayLogByStatus(@RequestParam(name = "appCode", required = true) String appCode,
                                        @RequestParam(name = "startTime", required = false) Long startTime,
                                        @RequestParam(name = "endTime", required = false) Long endTime){
        return analysisGatewayLogService.queryGatewayLogByStatus(appCode, startTime, endTime);
    }


}
