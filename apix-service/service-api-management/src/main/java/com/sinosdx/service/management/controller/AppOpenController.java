package com.sinosdx.service.management.controller;


import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.service.management.dao.entity.ApplicationSubscribe;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.ApplicationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Api(tags = "统一应用中台模块")
@RestController
@RequestMapping("/app/open")
public class AppOpenController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 查看应用列表（应用管理和资源市场都可用）
     *
     * @param developerId
     * @param isPublished
     * @param startTime
     * @param endTime
     * @return
     */
    @AuditLog(type = "查询应用列表", name = "应用")
    @ApiOperation("查看应用列表（应用管理和资源市场都可用）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "developerId", dataType = "Integer", required = false, value = "开发者id（传入此参数显示开发者关联的应用列表）"),
            @ApiImplicitParam(paramType = "query", name = "appName", dataType = "String", required = false, value = "应用名称"),
            @ApiImplicitParam(paramType = "query", name = "appCode", dataType = "String", required = false, value = "应用编号"),
            @ApiImplicitParam(paramType = "query", name = "appId", dataType = "Integer", required = false, value = "应用id"),
            @ApiImplicitParam(paramType = "query", name = "isPublished", dataType = "String", required = false, value = "是否发布到资源市场（传入此参数显示资源市场应用列表）"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "Long", required = false, value = "开始时间戳查询（根据创建时间）"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "Long", required = false, value = "结束时间戳查询（根据创建时间）"),
            @ApiImplicitParam(paramType = "query", name = "limit", dataType = "Integer", required = false, value = "每页条数"),
            @ApiImplicitParam(paramType = "query", name = "offset", dataType = "Integer", required = false, value = "页码")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @GetMapping("/list")
    public R<Object> getApplicationList(@RequestParam(value = "developerId", required = false) Integer developerId,
                                        @RequestParam(value = "appName", required = false) String appName,
                                        @RequestParam(value = "appCode", required = false) String appCode,
                                        @RequestParam(value = "appId", required = false) Integer appId,
                                        @RequestParam(value = "isPublished", required = false) String isPublished,
                                        @RequestParam(value = "startTime", required = false) Long startTime,
                                        @RequestParam(value = "endTime", required = false) Long endTime,
                                        @RequestParam(value = "limit", required = false) Integer limit,
                                        @RequestParam(value = "offset", required = false) Integer offset,
                                        @RequestParam(value = "market", required = false) Boolean market) {
        return applicationService.queryApplicationList(developerId, appName, appCode, appId, null,
                startTime, endTime, limit, offset, market);
    }

    /**
     * 查看应用详情
     *
     * @param appCode
     * @param developerId 开发者id（传入此参数显示开发者关联的应用详情，在资源市场列表和查询正使用的应用详情时不传）
     * @return
     */
    @AuditLog(type = "查询应用详情", name = "应用")
    @ApiOperation("查看应用详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "appCode", dataType = "String", required = true, value = "应用编号"),
            @ApiImplicitParam(paramType = "query", name = "developerId", dataType = "Integer", required = false, value = "开发者id（传入此参数显示开发者关联的应用详情，在资源市场列表和查询正使用的应用详情时不传）")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @GetMapping("/{appCode}")
    public R<Object> getApplicationDetails(@PathVariable("appCode") String appCode,
                                           @RequestParam(value = "developerId", required = false) Integer developerId) {
        return applicationService.queryApplicationDetails(appCode, developerId);
    }
    /**
     * 订阅应用服务（使用资源市场应用服务）
     *
     * @param appSubscribedCode
     * @return
     */
    @GetMapping("/subscribe/{appSubscribedCode}")
    public R<Object> appSubscribe(@PathVariable("appSubscribedCode") String appSubscribedCode) {
        return applicationService.appSubscribe(appSubscribedCode);
    }

    /**
     * 根据订阅编号查询订阅信息
     *
     * @param subscribeCode
     * @return
     */
    @GetMapping("/subscribe")
    public R<ApplicationSubscribe> queryAppCodeBySubscribeCode(@RequestParam String subscribeCode) {
        return applicationService.queryAppCodeBySubscribeCode(subscribeCode);
    }

    /**
     * 查询鉴权过滤器链
     *
     * @param appCode
     * @return
     */
    @GetMapping("/auth-plugin/name/list")
    public R<List<String>> queryAppAuthPluginNameList(@RequestParam String appCode) {
        return applicationService.queryAppAuthPluginNameList(appCode);
    }

}
