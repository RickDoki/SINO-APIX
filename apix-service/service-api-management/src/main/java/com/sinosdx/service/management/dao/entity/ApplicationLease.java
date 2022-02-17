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
 * @date 2020/12/7
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_lease")
public class ApplicationLease extends Entity<Integer> {
    private static final long serialVersionUID = -6977170472232822058L;

    /**
     * 承租应用编号
     */
    private String appLesseeCode;

    /**
     * 承租应用id
     */
    private Integer appLesseeId;

    /**
     * 出租应用编号(获取token的client_id)
     */
    private String appLessorCode;

    /**
     * 出租应用id
     */
    private Integer appLessorId;

    /**
     * 获取token的client_id
     */
    private String clientId;

    /**
     * 获取token的client_secret
     */
    private String clientSecret;

    private String creationByUsername;
    private String lastUpdatedByUsername;
    private String appLesseeName;
    private String appLessorName;
}
