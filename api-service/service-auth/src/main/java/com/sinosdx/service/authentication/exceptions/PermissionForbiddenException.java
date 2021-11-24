package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 权限不足
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class PermissionForbiddenException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public PermissionForbiddenException() {
        super();
    }

    public PermissionForbiddenException(Object data) {
        super.data = data;
    }

    public PermissionForbiddenException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public PermissionForbiddenException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public PermissionForbiddenException(String msg) {
        super(msg);
    }


}
