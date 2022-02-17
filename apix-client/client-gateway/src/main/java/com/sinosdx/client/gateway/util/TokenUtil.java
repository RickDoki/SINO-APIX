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
package com.sinosdx.client.gateway.util;

import com.sinosdx.client.gateway.callback.IResponseHandler;
import com.sinosdx.client.gateway.dto.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Slf4j
public class TokenUtil {

    private static String appToken = null;

    private static IResponseHandler apiResponseHandler;

    public static String buildUri(String gatewayPath, Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(gatewayPath);
        sb.append(request.getPath());

        StringBuilder queryPath = new StringBuilder();
        if (null != request.getQueryParams()) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                queryPath.append(entry.getKey());
                queryPath.append("=");
                queryPath.append(entry.getValue());
                queryPath.append("&");
            }
        }
        String queryPathStr = queryPath.toString();
        queryPathStr = (queryPathStr.length() < 1) ? ""
                : queryPathStr.substring(0, queryPathStr.length() - 1);
        if (queryPathStr.length() > 1) {
            sb.append("?");
            sb.append(queryPathStr);
        }
        return sb.toString();
    }
}


