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
package com.sinosdx.service.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenjian
 * @create 2021-06-25 9:53
 * @Description tenants/map 接口返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    /**
     * 租户名称
     */
    private String name;
    /**
     * 租户shortname/seqno
     */
    private String shortName;
    /**
     * 租户描述
     */
    private String description;
    /**
     * 租户uuid
     */
    private String id;
}
