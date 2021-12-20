package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 数据已存在异常
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class DataConflictException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public DataConflictException() {
        super();
    }

    public DataConflictException(Object data) {
        super.data = data;
    }

    public DataConflictException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public DataConflictException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public DataConflictException(String msg) {
        super(msg);
    }


}
