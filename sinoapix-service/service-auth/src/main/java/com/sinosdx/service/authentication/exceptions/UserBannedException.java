package com.sinosdx.service.authentication.exceptions;

import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBannedException extends RuntimeException {

    private static final long serialVersionUID = -3188777412905730630L;

    private Integer code;
    private String msg;
    private Object data;

    public UserBannedException(String msg) {
        super(msg);
        this.code = ResultCodeEnum.USER_BANNED.getCode();
    }

}