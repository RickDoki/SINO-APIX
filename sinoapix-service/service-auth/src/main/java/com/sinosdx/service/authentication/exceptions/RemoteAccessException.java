package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 远程访问异常
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class RemoteAccessException extends BaseException {

    private static final long serialVersionUID = -832464574076215195L;

    public RemoteAccessException() {
        super();
    }

    public RemoteAccessException(Object data) {
        super.data = data;
    }

    public RemoteAccessException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public RemoteAccessException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public RemoteAccessException(String msg) {
        super(msg);
    }

}
