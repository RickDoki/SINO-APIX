package com.sinosdx.service.management.enums;

import lombok.Getter;

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
    BASE_AUTH("base_auth", "BaseAuth"),
    OAUTH2("oauth2", "OAuth"),

    BLACK_LIST_IP("black_list_ip","BlacklistIp"),

    WHITE_LIST_IP("white_list_ip","WhitelistIp"),

    CORS("cors","Cors"),

    SIGN("sign","SignAuth"),

    ERROR_LOG("error_log","ErrorLog"),

    GZIP("gzip","Gzip"),

    HTTP_LOG("http_log","HttpLog"),

    PROXY_CACHE("proxy_cache","ProxyCache"),

    REAL_IP("real_ip","RealIp"),

    REPLAY_ATTACKS("replay_attacks","ReplayAttacks"),

    SENTINEL("sentinel","sentinel"),

    RESPONSE_REWRITE("response_rewrite","RespRewrite"),
    ;

    private final String type;
    private final String filterName;

    PluginTypeEnum(String type, String filterName) {
        this.type = type;
        this.filterName = filterName;
    }

//    C_BLACK_LIST_IP(-50),
//    C_WHITE_LIST_IP(-49),
//    C_CORS(-48),
//    C_AUTHORIZE(-47),
//    C_OAUTH(-46),
//    C_JWT(-45),
//    C_SIGN(-44),
//    C_ERROR_LOG(-43),
//    C_GZIP(-42),
//    C_HTTP_LOG(-41),
//    C_PROXY_CACHE(-40),
//    C_REAL_IP(-39),
//    C_REPLAY_ATTACKS(-38),

}
