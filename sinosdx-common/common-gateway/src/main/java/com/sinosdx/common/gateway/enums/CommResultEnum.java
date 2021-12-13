package com.sinosdx.common.gateway.enums;

import com.sinosdx.common.base.annotation.GlobalExpEnumType;
import com.sinosdx.common.base.result.enums.BaseEnum;
import com.sinosdx.common.base.result.enums.ExpEnumCodeUtil;

/**
 * @author pengjiahu
 * @date 2021-06-10 00:40
 * @description
 */
@GlobalExpEnumType(module = 10, kind = 90)
public enum CommResultEnum implements BaseEnum {

    /**
     *
     */
    REQUEST_EMPTY(1, "");

    private Integer code;

    private String message;

    CommResultEnum(Integer code, String message) {
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
