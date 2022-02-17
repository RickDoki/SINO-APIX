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
package com.sinosdx.common.gateway.plugin.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-api-management")
public interface ApplicationServiceFeign {

    /**
     * 根据订阅编号查询订阅信息
     *
     * @param subscribeCode
     * @return
     */
    @GetMapping("/app/open/subscribe")
    R<JSONObject> queryAppCodeBySubscribeCode(@RequestParam String subscribeCode);

    /**
     * 查询鉴权过滤器链
     *
     * @param appCode
     * @return
     */
    @GetMapping("/app/open/auth-plugin/name/list")
    R<List<String>> queryAppAuthPluginNameList(@RequestParam String appCode);
}
