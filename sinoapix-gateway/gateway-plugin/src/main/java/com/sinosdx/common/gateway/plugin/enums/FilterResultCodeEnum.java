package com.sinosdx.common.gateway.plugin.enums;

import com.sinosdx.common.base.result.enums.BaseEnum;

/**
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public enum FilterResultCodeEnum implements BaseEnum<Integer,String> {
    /**
     * 过滤器相关的 code 100000
     */
    TIMESTAMP_EMPTY(100001, "时间戳（timestamp）不存在"),
    NONCE_EMPTY(100002, "随机数（nonce）不存在"),
    INTERFACE_LIMITED(100003, "接口访问受限"),
    ;

    private Integer code;
    private String message;

    FilterResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.message;
    }

    public FilterResultCodeEnum build(String msg, Object... param) {
        this.message = String.format(msg, param);
        return this;
    }

    public FilterResultCodeEnum param(Object... param) {
        this.message = String.format(this.message, param);
        return this;
    }
}
