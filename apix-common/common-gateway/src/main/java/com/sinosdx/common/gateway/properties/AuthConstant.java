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
package com.sinosdx.common.gateway.properties;


/**
 * Auth constant
 * @author wendy
 */
public class AuthConstant {

    /**
     * AUTH_HEADER
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * AUTH_HEADER_PREFIX
     */
    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    /**
     * BASIC_HEADER_PREFIX
     */
    public static final String BASIC_HEADER_PREFIX = "Basic ";

    /**
     * AUTH_ACCESS_TOKEN
     */
    public static final String AUTH_ACCESS_TOKEN = "access_token";

    /**
     * 接口签名
     */
    public static final String AUTH_SIGN = "sign";

    /**
     * 接口签名时间戳
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * jwt
     */
    public static final String AUTH_JWT = "JWT";

}
