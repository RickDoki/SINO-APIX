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
