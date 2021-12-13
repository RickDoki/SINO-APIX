package com.sinosdx.service.authentication.exceptions;

import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidPasswordException extends RuntimeException {
    private static final long serialVersionUID = -6318064952917594899L;

    private Integer code;
    private String msg;

    public InvalidPasswordException(String msg) {
        super(msg);
        this.code = ResultCodeEnum.PWD_ERROR.getCode();
        this.msg = msg;
    }

}
