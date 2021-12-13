package com.sinosdx.service.event.bridge.enums;

import com.sinosdx.common.base.annotation.GlobalExpEnumType;
import com.sinosdx.common.base.result.enums.BaseEnum;
import com.sinosdx.common.base.result.enums.ExpEnumCodeUtil;

/**
 * @author pengjiahu
 * @date 2021-01-21 23:11
 * @description
 */
@GlobalExpEnumType(module = 80, kind = 1000)
public enum ExceptionEnum implements BaseEnum {
    /**
     * 枚举
     */
    EVENT_NOT_FOUND(1, "未查询到事件");

    private final Integer code;

    private final String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getDesc() {
        return this.message;
    }
}
