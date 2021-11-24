package com.sinosdx.service.event.bridge.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author pengjiahu
 * @date 2020-11-02
 * @description
 */
public enum ConvertTypeEnum {

    // 未知
    UNKNOWN(0),
    // 完整
    COMPLETE(1),
    // 部分
    SECTION(2),
    // 常量
    CONSTANT(3),
    // 模板
    TEMPLATE(4);

    @EnumValue
    private final int type;

    ConvertTypeEnum(int type) {
        this.type = type;
    }

    public static int fromType(String name) {
        for (ConvertTypeEnum convertTypeEnum : values()) {
            if (convertTypeEnum.name().equalsIgnoreCase(name)) {
                return convertTypeEnum.getValue();
            }
        }
        return UNKNOWN.getValue();
    }

    public static ConvertTypeEnum formType(Integer type) {
        for (ConvertTypeEnum convertTypeEnum : values()) {
            if (convertTypeEnum.getValue() == type) {
                return convertTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public int getValue() {
        return this.type;
    }
}
