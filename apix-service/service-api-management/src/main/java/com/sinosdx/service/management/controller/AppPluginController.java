package com.sinosdx.service.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.AppPluginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Api(tags = "统一应用中台模块")
@RestController
@RequestMapping("/app/plugin")
public class AppPluginController {

    @Autowired
    private AppPluginService appPluginService;

    /**
     * 服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @PostMapping()
    public R<Object> addAppPlugin(@RequestBody ApplicationPlugin applicationPlugin) {
        return appPluginService.addAppPlugin(applicationPlugin);
    }

    /**
     * 更新服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @PutMapping()
    public R<Object> updateAppPlugin(@RequestBody ApplicationPlugin applicationPlugin) {
        return appPluginService.updateAppPlugin(applicationPlugin);
    }

    /**
     * 获取插件详情
     *
     * @param pluginId
     * @param appCode
     * @return
     */
    @GetMapping("/{pluginId}/{appCode}")
    public R<Object> getAppPlugin(@PathVariable(value = "pluginId") String pluginId, @PathVariable(value = "appCode") String appCode) {
        return appPluginService.getAppPlugin(pluginId, appCode);
    }

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    @GetMapping("/configs")
    public R<JSONObject> queryPluginConfigs(@RequestParam String pluginType,
                                            @RequestParam String appCode) {
        return appPluginService.queryPluginConfigs(pluginType, appCode);
    }

    /**
     * 查询已订阅应用插件详情
     *
     * @param pluginId
     * @return
     */
    @GetMapping("/detail")
    public R<Object> getSubscribedAppPluginDetails(@RequestParam Integer pluginId) {
        return appPluginService.getSubscribedAppPluginDetails(pluginId);
    }


    @GetMapping("/randomKey")
    public R<Object> get() {
        return R.success(UUID.randomUUID().toString().split("-")[0]);
    }
}
