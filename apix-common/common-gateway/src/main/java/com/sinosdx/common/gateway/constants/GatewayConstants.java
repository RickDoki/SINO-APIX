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
package com.sinosdx.common.gateway.constants;

/**
 * @author pengjiahu
 * @date 2021-06-09 21:42
 * @description
 */
public interface GatewayConstants {

    String CLIENT_IP = "clientIp";

    String SYS = "sys";

    String API_NAME = "apiName";

    String API_CODE = "code";

    String REQ_PARAMS = "reqParams";

    String X_FORWARDED_FOR = "X-Forwarded-For";

    String OUT_NAME = "outName";

    String NONCE = "nonce";

    String SIGNATURE = "signature";

    String PARAM = "param";

    String DATA = "data";

    String REQUEST_HTTP_METHOD_PARAMETER_NAME = "http_method";

    String REQUEST_SERVICE_ID = "service_id";

    String REQUEST_URI_PARAMETER_NAME = "request_uri";

    String LOG_TOPIC = "log";

    String CACHED_REQUEST_BODY_STR = "cachedRequestBodyStr";

    String PATH = "x-path";

    String REAL_IP = "real-ip";

    String SERVICE_CODE = "service-code";

    String SYS_CLIENT_ID = "sys-client-id";

    String REDIS_PREFIX_APP_PLUGIN = "app_plugin:";

    String REDIS_PREFIX_AUTH = "gateway_auth_";
}
