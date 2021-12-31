package com.sinosdx.service.management.controller;


import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.ApplicationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Api(tags = "统一应用中台模块")
@RestController
@RequestMapping("/app/plugin")
public class AppPluginController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @PostMapping("/create")
    public R<Object> addAppPlugin(ApplicationPlugin applicationPlugin) {
        return applicationService.addAppPlugin(applicationPlugin);
    }

    /**
     * 更新服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @PostMapping("/update")
    public R<Object> updateAppPlugin(ApplicationPlugin applicationPlugin) {
        return applicationService.updateAppPlugin(applicationPlugin);
    }

    /**
     * 获取插件详情
     *
     * @param pluginId
     * @param appCode
     * @return
     */
    @GetMapping("/{pluginId}/{appCode}")
    public R<Object> getAppPlugin(@PathVariable(value = "pluginId") String pluginId,@PathVariable(value = "appCode") String appCode) {

        return null;
    }
}
