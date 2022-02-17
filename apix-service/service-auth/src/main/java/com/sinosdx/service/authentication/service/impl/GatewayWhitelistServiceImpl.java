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
package com.sinosdx.service.authentication.service.impl;

import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.service.GatewayWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author wendy
 * @date 2021/1/13
 */
@Slf4j
@Service
public class GatewayWhitelistServiceImpl implements GatewayWhitelistService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 查询网关白名单列表
     *
     * @return
     */
    @Override
    public R<Set<String>> queryGatewayWhitelist() {
        Set<String> set = redisTemplate.opsForSet().members(Constants.GATEWAY_WHITELIST);
        return R.success(set);
    }

    /**
     * 添加网关白名单列表
     *
     * @param paramMap
     * @return
     */
    @Override
    public R<Object> addGatewayWhitelist(Map<String, String> paramMap) {
        redisTemplate.opsForSet().add(Constants.GATEWAY_WHITELIST, paramMap.get("ip"));
        return R.success();
    }

    /**
     * 删除网关白名单列表
     *
     * @param ip
     * @return
     */
    @Override
    public R<Object> removeGatewayWhitelist(String ip) {
        redisTemplate.opsForSet().remove(Constants.GATEWAY_WHITELIST, ip);
        return R.success();
    }

    /**
     * 查询ip是否在白名单中
     *
     * @param ip
     * @return
     */
    @Override
    public R<Boolean> isMemberInGatewayWhitelist(String ip) {
        return R.success(redisTemplate.opsForSet().isMember(Constants.GATEWAY_WHITELIST, ip));
    }
}
