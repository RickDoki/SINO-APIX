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

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wendy
 * @date 2021/8/11
 */
@FeignClient("service-market")
public interface OmpServiceFeign {

    /**
     * 根据条件查询用户
     *
     * @param mobile
     * @param id
     * @param username
     * @return
     */
    @GetMapping("/omp/user")
    R<JSONObject> queryOmpUser(@RequestParam(value = "mobile", required = false) String mobile,
                               @RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "username", required = false) String username);
}
