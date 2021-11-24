package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class ParameterInvalidException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public ParameterInvalidException() {
        super();
    }

    public ParameterInvalidException(Object data) {
        super();
        super.data = data;
    }

    public ParameterInvalidException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public ParameterInvalidException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public ParameterInvalidException(String msg) {
        super(msg);
    }

}
