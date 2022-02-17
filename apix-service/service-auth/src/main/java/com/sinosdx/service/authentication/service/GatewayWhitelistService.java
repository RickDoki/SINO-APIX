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
package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author wendy
 * @date 2021/1/13
 */
@RestController
@RequestMapping("/auth/gateway/whitelist")
public interface GatewayWhitelistService {

    /**
     * 查询网关白名单列表
     *
     * @return
     */
    @GetMapping("/list")
    R<Set<String>> queryGatewayWhitelist();

    /**
     * 添加网关白名单列表
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/add")
    R<Object> addGatewayWhitelist(@RequestBody Map<String, String> paramMap);

    /**
     * 删除网关白名单列表
     *
     * @param ip
     * @return
     */
    @DeleteMapping("/remove")
    R<Object> removeGatewayWhitelist(@RequestParam("ip") String ip);

    /**
     * 查询ip是否在白名单中
     *
     * @param ip
     * @return
     */
    @GetMapping("/is/member")
    R<Boolean> isMemberInGatewayWhitelist(@RequestParam("ip") String ip);
}
