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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_subscribe")
public class ApplicationSubscribe extends Entity<Integer> {
    private static final long serialVersionUID = -6977170472232822058L;

    /**
     * 订阅者id
     */
    private Integer subscribeClientId;

    /**
     * 被订阅应用编号
     */
    private String appSubscribedCode;

    /**
     * 被订阅应用id
     */
    private Integer appSubscribedId;

    /**
     * 生成一段订阅code用于网关定位
     */
    private String appClientCode;

}
