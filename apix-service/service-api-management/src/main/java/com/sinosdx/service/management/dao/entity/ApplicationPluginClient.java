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
package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2021/12/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_plugin_client")
public class ApplicationPluginClient extends Entity<Integer> {
    private static final long serialVersionUID = 8999037431641813357L;

    private Integer sysClientId;
    private Integer appPluginId;
    private String pluginType;
    /**
     * 发布到网关的配置（包括服务发布方和订阅方）
     */
    private String pluginParams;
}
