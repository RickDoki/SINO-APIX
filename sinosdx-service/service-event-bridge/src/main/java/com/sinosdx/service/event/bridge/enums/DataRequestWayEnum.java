package com.sinosdx.service.event.bridge.enums;

import lombok.Getter;

/**
 * @author pengjiahu
 * @date 2020-11-12
 * @description
 */
@Getter
public enum DataRequestWayEnum {

    // 未知
    UNKNOWN,
    // body
    BODY,
    // params
    PARAMS;

    public static DataRequestWayEnum form(String type) {
        for (DataRequestWayEnum dataRequestWayEnum : values()) {
            if (dataRequestWayEnum.name().equals(type)) {
                return dataRequestWayEnum;
            }
        }
        return UNKNOWN;
    }
}
