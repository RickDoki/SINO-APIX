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

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysUser;

/**
 * @author wendy
 * @date 2021/12/21
 */
public interface SysClientService {

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    R<Object> queryClientByUserId(Integer sysUserId);

    /**
     * 根据clientId查询user
     *
     * @param sysClientId
     * @return
     */
    R<SysUser> queryUserByClientId(Integer sysClientId);
}
