package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class MethodNotAllowException extends BaseException {

    private static final long serialVersionUID = -3813290937049524713L;

    public MethodNotAllowException() {
        super();
    }

    public MethodNotAllowException(Object data) {
        super.data = data;
    }

    public MethodNotAllowException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public MethodNotAllowException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public MethodNotAllowException(String msg) {
        super(msg);
    }

}
