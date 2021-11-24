package com.sinosdx.service.event.bridge.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author pengjiahu
 * @date 2020-11-02
 * @description
 */
public enum TargetTypeEnum {

    // 未知
    UNKNOWN(0),
    // mq
    MQ(1),
    // http
    HTTP(2),
    // 短信
    SMS(3),
    // 邮件
    EMAIL(4),
    // 钉钉机器人
    DINGTALK_NOTICE(5),
    // 企业微信机器人
    WORK_WEIXIN_NOTICE(6),
    // 内部服务API
    INNER_API(7),
    // Redis缓存
    REDIS(8);

    @EnumValue
    private final Integer type;

    TargetTypeEnum(Integer type) {
        this.type = type;
    }

    public static Integer fromType(String name) {
        for (TargetTypeEnum targetTypeEnum : values()) {
            if (targetTypeEnum.name().equalsIgnoreCase(name)) {
                return targetTypeEnum.getValue();
            }
        }
        return UNKNOWN.getValue();
    }

    public static TargetTypeEnum formType(Integer type) {
        for (TargetTypeEnum targetTypeEnum : values()) {
            if (targetTypeEnum.getValue() == type) {
                return targetTypeEnum;
            }
        }
        return UNKNOWN;
    }

    public int getValue() {
        return this.type;
    }
}
