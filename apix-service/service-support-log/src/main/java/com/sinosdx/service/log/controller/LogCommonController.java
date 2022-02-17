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
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.log.constants.Constants;
import com.sinosdx.service.log.dao.entity.*;
import com.sinosdx.service.log.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Validated
@RestController
@RequestMapping("log/common")
@Api("logCommon日志")
public class LogCommonController {

    @Autowired
    private IApiLogService apiLogService;

    @Autowired
    private IErrorLogService errorLogService;

    @Autowired
    private IBizLogService bizLogService;

    @Autowired
    private IGatewayLogService gatewayLogService;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private IAuditLogService auditLogService;

    @PostMapping(path = "{logType}", consumes = "text/html;charset=UTF-8")
    public void saveLog(@PathVariable String logType, @RequestBody String string) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        log.debug("API,接收到[{}]日志信息：{}", logType, JSON.toJSONString(jsonObject));
        switch (logType) {
            case Constants.API:
                ApiLog apiLog = jsonObject.toJavaObject(ApiLog.class);
                apiLogService.save(apiLog);
                break;
            case Constants.ERROR:
                ErrorLog errorLog = jsonObject.toJavaObject(ErrorLog.class);
                errorLogService.save(errorLog);
                break;
            case Constants.LOGIN:
                LoginLog loginLog = jsonObject.toJavaObject(LoginLog.class);
                loginLogService.save(loginLog);
                break;
            case Constants.BIZ:
                BizLog bizLog = jsonObject.toJavaObject(BizLog.class);
                bizLogService.save(bizLog);
                break;
            case Constants.GATEWAY:
                GatewayLog gatewayLog = jsonObject.toJavaObject(GatewayLog.class);
                gatewayLogService.save(gatewayLog);
                break;
            case Constants.AUDIT:
                AuditLog auditLog = jsonObject.toJavaObject(AuditLog.class);
                auditLogService.save(auditLog);
                break;
            default:
                log.error("未匹配到类型");
                break;
        }
    }

}
