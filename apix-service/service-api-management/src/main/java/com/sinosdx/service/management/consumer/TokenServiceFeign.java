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
import com.sinosdx.service.management.dao.entity.ClientAppSecret;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wendy
 * @date 2022/1/6
 */
@FeignClient("service-auth")
@RequestMapping("/auth/token")
public interface TokenServiceFeign {

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    @PostMapping("/secretKey")
    R<Object> saveClientAppSecretKey(@RequestBody ClientAppSecret secret);

    /**
     * 查询客户端的secret
     *
     * @param appCode
     * @return
     */
    @GetMapping("/secret")
    R<ClientAppSecret> querySecretByAppCode(@RequestParam("secretKey") String appCode);

    /**
     * 删除secret
     *
     * @param appCode
     * @return
     */
    @DeleteMapping("/secret")
    R<Object> deleteClientAppSecret(@RequestParam String appCode);
}
