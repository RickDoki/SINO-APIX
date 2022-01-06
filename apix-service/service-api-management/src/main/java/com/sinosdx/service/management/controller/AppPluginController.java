package com.sinosdx.service.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.ApplicationService;
import io.swagger.annotations.Api;
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
    @PostMapping()
    public R<Object> addAppPlugin(@RequestBody ApplicationPlugin applicationPlugin) {
        return applicationService.addAppPlugin(applicationPlugin);
    }

    /**
     * 更新服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @PutMapping()
    public R<Object> updateAppPlugin(@RequestBody ApplicationPlugin applicationPlugin) {
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
    public R<Object> getAppPlugin(@PathVariable(value = "pluginId") String pluginId, @PathVariable(value = "appCode") String appCode) {
        return applicationService.getAppPlugin(pluginId,appCode);
    }

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    @GetMapping("/plugin/configs")
    public R<JSONObject> queryPluginConfigs(@RequestParam String pluginType,
                                            @RequestParam String appCode) {
        return applicationService.queryPluginConfigs(pluginType, appCode);
    }
}
