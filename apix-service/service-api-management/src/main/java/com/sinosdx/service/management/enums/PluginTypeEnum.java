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

    BLACK_LIST_IP("black_list_ip","black_list_ip"),

    WHITE_LIST_IP("white_list_ip","white_list_ip"),

    CORS("cors","cors"),

    SIGN("sign","sign"),

    ERROR_LOG("error_log","error_log"),

    GZIP("gzip","gzip"),

    HTTP_LOG("http_log","http_log"),

    PROXY_CACHE("proxy_cache","proxy_cache"),

    REAL_IP("real_ip","real_ip"),

    REPLAY_ATTACKS("replay_attacks","replay_attacks"),

    SENTINEL("sentinel","sentinel"),
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
