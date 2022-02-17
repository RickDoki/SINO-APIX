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
package com.sinosdx.client.gateway.http;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public enum RespErrCode {
    AUTH_INVALID_TOKEN,
    AUTH_TOKEN_EXPIRED,
    AUTH_INVALID_ACCOUNT_OR_PSW,
    AUTH_INVALID_IP,
    AUTH_INVALID_DOMAIN,
    ACL_PERMISSION_DENIED,
    ACL_BLACKLIST,
    INVALID_API,
    FLOW_LIMITED,
    INVALID_PARAMETER,
    DEST_SERVICE_UNAVAILABLE,
    DEST_SERVICE_RESP_TIMEOUT,
    GATEWAY_RESP_TIMEOUT,
    GATEWAY_UNAVAILABLE,
    CMPT_EXECUTE_TIMEOUT,
    ConnectTimeoutException,
    SocketTimeoutException,
    UNKNOWN_ERR;
}


