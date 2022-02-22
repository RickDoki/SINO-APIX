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
package com.sinosdx.common.gateway.plugin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengjiahu
 * @date 2021-12-30 16:09
 * @description
 */
@AllArgsConstructor
@Getter
public enum FilterOrderEnum {
    /**
     * global
     */
    G_DEFAULT(-52),
    G_AUTHORIZE(-44),
    G_BASE(-100),
    G_CACHE_POST_BODY(-99),
    G_REQUEST_LOG(-98),
    G_REQUEST_PRINT(-97),
    G_SAFE_CONTROL(-96),
    G_AFTER(-95),
    G_GRAY_LOAD_BALANCER(10250),
    /**
     * custom
     */
    C_BLACK_LIST_IP(-50),
    C_WHITE_LIST_IP(-49),
    C_CORS(-48),
    C_AUTHORIZE(-47),
    C_OAUTH(-46),
    C_JWT(-45),
    C_SIGN(-43),
    C_ERROR_LOG(-42),
    C_GZIP(-43),
    C_HTTP_LOG(-42),
    C_PROXY_CACHE(-41),
    C_REAL_IP(-40),
    C_REPLAY_ATTACKS(-39),
    ;

    private final int order;
}
