package com.sinosdx.service.management.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.result.R;

/**
 * @author wendy
 * @date 2022/1/12
 */
public interface AppPluginService {

    /**
     * 服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    R<Object> addAppPlugin(ApplicationPlugin applicationPlugin);

    /**
     * 修改服务插件
     *
     * @param applicationPlugin
     * @return
     */
    R<Object> updateAppPlugin(ApplicationPlugin applicationPlugin);

    /**
     * 获取服务插件
     *
     * @param pluginId
     * @param appCode
     * @return
     */
    R<Object> getAppPlugin(String pluginId, String appCode);

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    R<JSONObject> queryPluginConfigs(String pluginType, String appCode);
}
