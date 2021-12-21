package com.sinosdx.service.user.enums;

import lombok.Getter;

/**
 * @author wendy
 * @date 2021/12/20
 */
@Getter
public enum ClientResourceEnum {

    /**
     * 资源类型
     */
    USER("user", "用户");

    private final String name;
    private final String desc;

    ClientResourceEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
