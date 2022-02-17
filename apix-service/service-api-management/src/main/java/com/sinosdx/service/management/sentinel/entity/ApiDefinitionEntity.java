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
package com.sinosdx.service.management.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author shenjian
 * @create 2021-12-30 16:21
 * @Description  根据app code 和 路径名称确定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiDefinitionEntity {
    //app code
    private String app;

    //自定义 api 名称 -> resource
    private String apiName;

    //保存具体的 api 信息
    private Set<ApiEntity> predicateItems;

}
