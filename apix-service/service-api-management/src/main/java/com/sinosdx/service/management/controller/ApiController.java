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
package com.sinosdx.service.management.controller;

import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.service.management.controller.vo.ApiVersionVo;
import com.sinosdx.service.management.controller.vo.ApiVo;
import com.sinosdx.service.management.controller.vo.AppApiReq;
import com.sinosdx.service.management.dao.entity.Api;
import com.sinosdx.service.management.dao.entity.ApiVersion;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.ApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2020/12/10
 */
@io.swagger.annotations.Api(tags = "统一应用中台模块")
@RestController
@RequestMapping("/app/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    /**
     * 创建API
     *
     * @param apiVo
     * @return
     */
    @AuditLog(type = "创建", name = "API")
    @ApiOperation("创建API")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "apiName", dataType = "String", required = true, value = "api名称"),
            @ApiImplicitParam(paramType = "body", name = "description", dataType = "String", required = false, value = "api描述"),
            @ApiImplicitParam(paramType = "body", name = "apiUrl", dataType = "String", required = true, value = "api路径"),
            @ApiImplicitParam(paramType = "body", name = "requestMethod", dataType = "String", required = true, value = "api请求方法"),
            @ApiImplicitParam(paramType = "body", name = "apiVersion", dataType = "String", required = true, value = "api接口版本号")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @PostMapping("/create")
    public R<Object> createApi(@RequestBody ApiVo apiVo) {
        R<Object> data = apiService.createApi(new Api(apiVo));
        if (data.isSuccess()) {
            return R.success(new ApiVo((Api) data.getData()));
        } else {
            return data;
        }
    }

    /**
     * 修改API
     *
     * @param apiId
     * @param apiVo
     * @return
     */
    @AuditLog(type = "更新", name = "API")
    @ApiOperation("修改API")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "apiName", dataType = "String", required = false, value = "api名称"),
            @ApiImplicitParam(paramType = "body", name = "description", dataType = "String", required = false, value = "api描述"),
            @ApiImplicitParam(paramType = "body", name = "apiUrl", dataType = "String", required = false, value = "api路径"),
            @ApiImplicitParam(paramType = "body", name = "requestMethod", dataType = "String", required = false, value = "api请求方法"),
            @ApiImplicitParam(paramType = "body", name = "apiVersion", dataType = "String", required = false, value = "api接口版本号"),
            @ApiImplicitParam(paramType = "body", name = "isPublished", dataType = "String", required = false, value = "api是否发布")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @PutMapping("/{apiId}")
    public R<Object> updateApi(@PathVariable("apiId") Integer apiId, @RequestBody ApiVo apiVo) {
        apiVo.setApiId(apiId);
        return apiService.updateApi(apiVo);
    }

    /**
     * 删除API
     *
     * @param apiId
     * @return
     */
    @AuditLog(type = "删除", name = "API")
    @ApiOperation("删除API")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "apiId", dataType = "Integer", required = true, value = "apiId")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @DeleteMapping("/{apiId}")
    public R<Object> deleteApi(@PathVariable("apiId") Integer apiId) {
        return apiService.deleteApi(apiId);
    }

    /**
     * 查询API列表
     *
     * @param apiName
     * @param apiUrl
     * @param requestMethod
     * @param apiVersion
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @param developerId
     * @return
     */
    @AuditLog(type = "查询列表", name = "API")
    @ApiOperation("查询API列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "apiName", dataType = "String", required = false, value = "api名称"),
            @ApiImplicitParam(paramType = "query", name = "apiUrl", dataType = "String", required = false, value = "api路径"),
            @ApiImplicitParam(paramType = "query", name = "requestMethod", dataType = "String", required = false, value = "api请求方法"),
            @ApiImplicitParam(paramType = "query", name = "apiVersion", dataType = "String", required = false, value = "api接口版本号"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "Long", required = false, value = "开始时间戳查询（根据创建时间）"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "Long", required = false, value = "结束时间戳查询（根据创建时间）"),
            @ApiImplicitParam(paramType = "query", name = "limit", dataType = "Integer", required = false, value = "每页条数"),
            @ApiImplicitParam(paramType = "query", name = "offset", dataType = "Integer", required = false, value = "页码")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功"),
    })
    @GetMapping("/list")
    public R<Object> queryApiList(@RequestParam(value = "apiName", required = false) String apiName,
                           @RequestParam(value = "apiUrl", required = false) String apiUrl,
                           @RequestParam(value = "requestMethod", required = false) String requestMethod,
                           @RequestParam(value = "apiVersion", required = false) String apiVersion,
                           @RequestParam(value = "startTime", required = false) Long startTime,
                           @RequestParam(value = "endTime", required = false) Long endTime,
                           @RequestParam(value = "limit", required = false) Integer limit,
                           @RequestParam(value = "offset", required = false) Integer offset,
                           @RequestParam(value = "developerId", required = false) Integer developerId) {
        return apiService.queryApiList(apiName, apiUrl, requestMethod, apiVersion, startTime, endTime, limit, offset, developerId);
    }

    /**
     * 创建api新版本
     *
     * @param apiVersion
     * @return
     */
//    @AuditLog(type = "创建新版本", name = "API")
//    @PostMapping("/create/version")
//    public R<Object> addNewApiVersion(@RequestBody ApiVersion apiVersion) {
//        return apiService.addNewApiVersion(apiVersion);
//    }

    /**
     * 修改API版本
     *
     * @param apiVersionId
     * @param apiVersionVo
     * @return
     */
//    @ApiOperation("修改API版本")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "body", name = "apiName", dataType = "String", required = false, value = "api名称"),
//            @ApiImplicitParam(paramType = "body", name = "description", dataType = "String", required = false, value = "api描述"),
//            @ApiImplicitParam(paramType = "body", name = "url", dataType = "String", required = false, value = "api路径"),
//            @ApiImplicitParam(paramType = "body", name = "requestMethod", dataType = "String", required = false, value = "api请求方法"),
//            @ApiImplicitParam(paramType = "body", name = "version", dataType = "String", required = false, value = "api接口版本号"),
//            @ApiImplicitParam(paramType = "body", name = "isPublished", dataType = "String", required = false, value = "api是否发布")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 0, message = "请求成功"),
//    })
//    @AuditLog(type = "修改新版本", name = "API")
//    @PutMapping("/version/{apiVersionId}")
//    public R<Object> updateApiVersion(@PathVariable("apiVersionId") Integer apiVersionId, @RequestBody ApiVersionVo apiVersionVo) {
//        apiVersionVo.setApiVersionId(apiVersionId);
//        return apiService.modifyApiVersion(apiVersionVo);
//    }

    /**
     * 查询api模板列表
     *
     * @param name
     * @param url
     * @param requestMethod
     * @param limit
     * @param offset
     * @return
     */
    @AuditLog(type = "查询模板列表", name = "API")
    @GetMapping("/template/list")
    public R<Object> queryApiTemplateList(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "url", required = false) String url,
                                          @RequestParam(value = "requestMethod", required = false) String requestMethod,
                                          @RequestParam(value = "limit", required = false) Integer limit,
                                          @RequestParam(value = "offset", required = false) Integer offset) {
        return apiService.queryApiTemplateList(name, url, requestMethod, limit, offset);
    }

    /**
     * 查看api模板
     *
     * @param id
     * @return
     */
    @AuditLog(type = "查询模板详情", name = "API")
    @GetMapping("/template/{id}")
    public R<Object> getApiTemplate(@PathVariable(value = "id") Integer id) {
        return apiService.getApiTemplate(id);
    }

    /**
     * 查看api详情
     *
     * @param apiId
     * @return
     */
    @AuditLog(type = "查询API详情", name = "API")
    @GetMapping("/{apiId}")
    public R<Object> queryApiDetail(@PathVariable("apiId") Integer apiId) {
        return apiService.queryApiDetail(apiId);
    }

    /**
     * 根据版本 版本id 获取 api
     */
    @AuditLog(type = "根据appCode 及 appVersionId 查询API集合", name = "API")
    @PostMapping("/queryApiList")
    public R<Object> queryApiList(@RequestBody AppApiReq req) {
        return apiService.queryApiListByAppVersionId(req.getAppCode(),req.getAppVersionId());
    }
}
