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

import com.sinosdx.service.authentication.dao.entity.GatewayBlacklist;
import com.sinosdx.service.authentication.result.R;

import java.util.Map;

/**
 * @author wendy
 * @date 2021/1/5
 */
public interface GatewayBlacklistService {

    /**
     * 创建黑名单
     *
     * @param gatewayBlacklist
     * @return
     */
    R<GatewayBlacklist> createGatewayBlacklist(GatewayBlacklist gatewayBlacklist);

    /**
     * 修改黑名单
     *
     * @param id
     * @param gatewayBlacklist
     * @return
     */
    R<GatewayBlacklist> updateGatewayBlacklist(Integer id, GatewayBlacklist gatewayBlacklist);

    /**
     * 删除黑名单
     *
     * @param id
     * @return
     */
    R<Object> deleteGatewayBlacklist(Integer id);

    /**
     * 查询黑名单列表
     *
     * @param type
     * @param content
     * @param limit
     * @param offset
     * @return
     */
    R<Map<String, Object>> queryGatewayBlacklist(String type, String content, Integer limit, Integer offset);

    /**
     * 查询黑名单
     *
     * @param type
     * @param content
     * @return
     */
    R<Map<String, String>> queryGatewayBlacklist(String type, String content);
}
