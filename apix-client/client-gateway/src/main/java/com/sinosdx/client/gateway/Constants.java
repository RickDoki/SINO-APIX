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
package com.sinosdx.client.gateway;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public class Constants {

    public static final String SIGN_SECRET = "api-gateway";
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static final String ENCODING = "UTF-8";
    public static final String USER_AGENT = "api-gateway-client";
    public static final String LF = "\n";
    public static final String SPE1 = ",";
    public static final String SPE2 = ":";
    public static final String SPE3 = "&";
    public static final String SPE4 = "=";
    public static final String SPE5 = "?";
    public static final int DEFAULT_TIMEOUT = 1000;
    public static final String CA_HEADER_TO_SIGN_PREFIX_SYSTEM = "X-Ca-";
    public static final String HEADER_KEY_REQ_ID = "api_gateway_reqID";
    public static final Integer DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_CONN = 200;
    public static final Integer DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_PER_ROUTE = 100;
    public static final String HEADER_APP_ID_KEY = "api_gateway_auth_app_id";
    public static final String HEADER_APP_PASSWORD_KEY = "api_gateway_auth_app_password";
    public static String HEADER_TOKEN_KEY = "api_gateway_auth_token";
    public static Integer DEFAULT_HTTP_REQ_EXPIRED_TIME = 10000;
}


