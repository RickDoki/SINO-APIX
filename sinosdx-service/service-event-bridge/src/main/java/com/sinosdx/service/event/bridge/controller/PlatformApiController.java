package com.sinosdx.service.event.bridge.controller;

import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.service.event.bridge.service.IPlatformApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjiahu
 * @date 2020-11-20 17:30
 * @description
 */
@RestController
@ResponseResult
@Validated
@RequestMapping("platform/api")
@Api(tags = "平台api")
public class PlatformApiController {

    @Autowired
    private IPlatformApiService platformApiService;


    @ApiOperation(value = "查询事件总线详情")
    @GetMapping("{serviceName}")
    public Object getApiList(@PathVariable("serviceName") String serviceName) {
        return platformApiService.getApiList(serviceName);
    }

}
