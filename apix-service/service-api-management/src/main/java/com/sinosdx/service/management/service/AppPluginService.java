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
package com.sinosdx.service.management.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.dao.entity.SysClient;
import com.sinosdx.service.management.result.R;

import java.util.List;

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

    /**
     * 订阅时处理各服务插件及绑定关系
     *
     * @param appPlugins
     * @param sysClient
     */
    void processPlugin(List<ApplicationPlugin> appPlugins, SysClient sysClient);

    /**
     * 后台重新给用户订阅服务（用于服务上架、插件变更操作）
     *
     * @param appCode
     */
    void reSubscribeApp(String appCode);

    /**
     * 查询已订阅应用插件详情
     *
     * @param pluginId
     * @return
     */
    R<Object> getSubscribedAppPluginDetails(Integer pluginId);
}
