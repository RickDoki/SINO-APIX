package com.sinosdx.common.gateway.exception;

import com.sinosdx.common.base.exceptions.BaseException;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;

/**
 * @author pengjiahu
 * @date 2021-06-10 00:40
 * @description
 */
public class CustomException extends BaseException {

    private static final long serialVersionUID = 3723471287889297081L;

    public CustomException() {
        super();
    }

    public CustomException(Object data) {
        super();
        super.data = data;
    }

    public CustomException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public CustomException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public CustomException(String msg) {
        super(msg);
    }
}
