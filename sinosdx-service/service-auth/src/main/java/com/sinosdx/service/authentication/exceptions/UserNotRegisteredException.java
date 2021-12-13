package com.sinosdx.service.authentication.exceptions;

import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotRegisteredException extends RuntimeException {

    private static final long serialVersionUID = -5645133786666659153L;

    private Integer code;
    private String msg;
    private Object data;

    public UserNotRegisteredException(String msg) {
        super(msg);
        this.code = ResultCodeEnum.USER_NOT_REGISTERED.getCode();
    }

}