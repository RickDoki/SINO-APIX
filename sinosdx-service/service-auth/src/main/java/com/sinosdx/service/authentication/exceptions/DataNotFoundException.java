package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 数据没有找到异常
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class DataNotFoundException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(Object data) {
        super();
        super.data = data;
    }

    public DataNotFoundException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public DataNotFoundException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public DataNotFoundException(String msg) {
        super(msg);
    }


}
