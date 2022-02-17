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
package com.sinosdx.service.management.consumer;

import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wendy
 * @date 2021/7/22
 */
@FeignClient("service-support-log")
public interface SupportLogServiceFeign {

    @GetMapping("gateway/log/queryListByAppCode")
    R<Object> queryListByAppCode(@RequestParam(name = "appCodes", required = false) List<String> appCodes,
                     @RequestParam(name = "startTime", required = false) Long startTime,
                     @RequestParam(name = "endTime", required = false) Long endTime);

    @GetMapping("gateway/log/queryGatewayLogByStatus")
    R<Object> queryGatewayLogByStatus(@RequestParam(name = "appCode", required = false) String appCode,
                                      @RequestParam(name = "requestUri", required = false) String requestUri,
                                             @RequestParam(name = "startTime", required = false) Long startTime,
                                             @RequestParam(name = "endTime", required = false) Long endTime);
}
