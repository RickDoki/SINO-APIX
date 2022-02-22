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
public interface SystemHeader {

    String HEADER_APP_ID_KEY = "api_gateway_auth_app_id";

    String HEADER_APP_PASSWORD_KEY = "api_gateway_auth_app_password";

    String HEADER_TOKEN_KEY = "api_gateway_auth_token";

    String HEADER_KEY_REQ_ID = "api_gateway_reqID";
}
