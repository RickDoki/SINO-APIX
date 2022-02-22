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
package com.sinosdx.service.management.controller.vo;

import com.sinosdx.service.management.dao.entity.ApplicationLease;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLeaseVo implements Serializable {
    private static final long serialVersionUID = -4366172921919295701L;

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

    public ApplicationLeaseVo(ApplicationLease applicationLease) {
        this.appLesseeId = applicationLease.getAppLesseeId();
        this.appLesseeCode = applicationLease.getAppLesseeCode();
        this.appLessorId = applicationLease.getAppLessorId();
        this.appLessorCode = applicationLease.getAppLessorCode();
        this.appLessorCode = applicationLease.getAppLessorCode();
        this.clientId = applicationLease.getClientId();
        this.clientSecret = applicationLease.getClientSecret();
    }
}
