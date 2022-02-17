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

import com.sinosdx.service.management.dao.entity.UpstreamServer;
import com.sinosdx.service.management.result.R;

/**
 * @author wendy
 * @date 2021/9/14
 */
public interface UpstreamServerService {

    /**
     * 查询上游服务列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryUpstreamServerList(String name, Integer limit, Integer offset);

    /**
     * 查询上游服务详情
     *
     * @param id
     * @return
     */
    R<Object> queryUpstreamServerDetail(Integer id);

    /**
     * 新增上游服务
     *
     * @param upstreamServer
     * @return
     */
    R<Object> addUpstreamServer(UpstreamServer upstreamServer);

    /**
     * 修改上游服务
     *
     * @param upstreamServer
     * @return
     */
    R<Object> updateUpstreamServer(UpstreamServer upstreamServer);

    /**
     * 删除上游服务
     *
     * @param id
     * @return
     */
    R<Object> deleteUpstreamServer(Integer id);
}
