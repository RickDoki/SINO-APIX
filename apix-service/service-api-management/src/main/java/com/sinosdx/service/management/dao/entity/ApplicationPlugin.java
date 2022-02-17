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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 服务插件
 *
 * @author wendy
 * @date 2021/12/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_plugin")
public class ApplicationPlugin extends Entity<Integer> {
    private static final long serialVersionUID = -7133398603981531070L;

    private Integer appId;
    private String appCode;
    private String pluginType;
    /**
    * 服务发布方的配置
    */
    private String pluginParams;
    private Integer enabled;
    @TableField(exist = false)
    private List<ApplicationPluginClient> applicationPluginClients;

}
