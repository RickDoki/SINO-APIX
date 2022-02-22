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
package com.sinosdx.service.authentication.consumer;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.authentication.dao.entity.Application;
import com.sinosdx.service.authentication.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-api-management")
public interface ApplicationServiceFeign {

    /**
     * 根据code查询app
     *
     * @param appCode
     * @return
     */
    @GetMapping("/app/query")
    R<Application> queryAppByAppCode(@RequestParam(value = "appCode") String appCode);

    /**
     * 通过查询clientId验证ApplicationLease是否存在
     *
     * @param clientId
     * @return
     */
    @PostMapping("/app/lease/verify/{clientId}")
    R<Boolean> verifyClientId(@PathVariable("clientId") String clientId);

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    @GetMapping("/app/plugin/configs")
    R<JSONObject> queryPluginConfigs(@RequestParam String pluginType,
                                            @RequestParam String appCode);
}
