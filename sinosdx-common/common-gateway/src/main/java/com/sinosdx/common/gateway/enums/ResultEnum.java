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
public enum ResultEnum implements BaseEnum {

    /**
     * 当前请求参数为空或数据缺失
     */
    REQUEST_EMPTY(1, "当前请求参数为空或数据缺失"),
    /**
     * 黑名单拒绝请求
     */
    BLACKLIST_IP(10, "The current IP [%s] is in the blacklist, reject request"),
    /**
     * 非白名单拒绝请求
     */
    WHITELIST_IP(11, "The current IP [%s] is not in the whitelist, reject the request"),
    /**
     * 非法请求
     */
    UN_SAFE_REQUEST(110, "非法请求，请稍后重试");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
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
