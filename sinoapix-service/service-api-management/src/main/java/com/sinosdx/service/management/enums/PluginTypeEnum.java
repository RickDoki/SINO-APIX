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
    OAUTH2("oauth2", "OAuth");

    private final String type;
    private final String filterName;

    PluginTypeEnum(String type, String filterName) {
        this.type = type;
        this.filterName = filterName;
    }

}
