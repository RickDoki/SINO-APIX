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
package com.sinosdx.service.management.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author wendy
 * @date 2021/12/17
 */
@Getter
public enum PluginTypeEnum {

    /**
     * 插件类型
     */
    JWT("jwt", "Jwt"),
    BASE_AUTH("base_auth", "BasicAuth"),
    OAUTH2("oauth2", "OAuth"),

    BLACK_LIST_IP("black_list_ip", "BlacklistIp"),

    WHITE_LIST_IP("white_list_ip", "WhitelistIp"),

    CORS("cors", "Cors"),

    SIGN("sign", "SignAuth"),

    ERROR_LOG("error_log", "ErrorLog"),

    GZIP("gzip", "Gzip"),

    HTTP_LOG("http_log", "HttpLog"),

    PROXY_CACHE("proxy_cache", "ProxyCache"),

    REAL_IP("real_ip", "RealIp"),

    REPLAY_ATTACKS("replay_attacks", "ReplayAttacks"),

    SENTINEL("sentinel", "sentinel"),

    RESPONSE_REWRITE("response_rewrite", "RespRewrite"),
    ;

    private final String type;
    private final String filterName;

    PluginTypeEnum(String type, String filterName) {
        this.type = type;
        this.filterName = filterName;
    }

    private static Map<String, PluginTypeEnum> map;

    static {
        map = Arrays.stream(values()).collect(Collectors.toMap(PluginTypeEnum::getType, UnaryOperator.identity()));
    }

    public static PluginTypeEnum getPluginTypeEnum(String type) {
        return map.get(type);
    }

}
