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
package com.sinosdx.service.user.service;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysOrg;

/**
 * @author wendy
 * @date 2021/9/24
 */
public interface SysOrgService {

    /**
     * 更新组织
     *
     * @param sysOrg
     * @return
     */
    R<Object> updateOrg(SysOrg sysOrg);

    /**
     * 查询组织列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryOrgList(String name, Integer limit, Integer offset);

}
