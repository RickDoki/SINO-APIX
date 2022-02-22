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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public class SignUtil {

    public static String sign(String path, String appID, String appAuthKey, String requestId) {
        try {
            path = path.replace("//", "/");
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = "UTF-8".getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            return bytes2Hex(hmacSha256.doFinal(
                    buildStringToSign(path, appID, appAuthKey, requestId).getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildStringToSign(String path, String appID, String appAuthKey,
            String requestId) {
        return path + appID + appAuthKey + requestId;
    }

    private static String buildHeaders(Map<String, String> headers,
            List<String> signHeaderPrefixList) {
        StringBuilder sb = new StringBuilder();
        if (null != signHeaderPrefixList) {
            signHeaderPrefixList.remove("api_gateway_auth_app_id");
            signHeaderPrefixList.remove("Accept");
            signHeaderPrefixList.remove("Content-MD5");
            signHeaderPrefixList.remove("Content-Type");
            signHeaderPrefixList.remove("Date");
            Collections.sort(signHeaderPrefixList);
            if (null != headers) {
                Map<String, String> sortMap = new TreeMap<>();
                sortMap.putAll(headers);
                StringBuilder signHeadersStringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                    ;
                }

                headers.put("api_gateway_auth_app_password", signHeadersStringBuilder.toString());
            }
        }
        return sb.toString();
    }

    private static String bytes2Hex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        for (byte aByte : bytes) {
            int digital = aByte;

            if (digital < 0) {
                digital += 256;
            }
            if (Integer.toHexString(digital).length() == 1) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(digital));
        }
        return hexStr.toString();
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}


