package com.sinosdx.service.event.bridge.service.impl.process;

import com.sinosdx.common.base.exceptions.BaseException;

/**
 * @author pengjiahu
 * @date 2020-12-02 16:01
 * @description
 */
public class NeedRetryException extends BaseException {

    private static final long serialVersionUID = 3721038715649297081L;

    public NeedRetryException() {
        super();
    }

    public NeedRetryException(Object data) {
        super.data = data;
    }

    public NeedRetryException(Integer code, String message, Object data) {
        super.code = String.valueOf(code);
        super.message = message;
        super.data = data;
    }

    public NeedRetryException(String message, Object data) {
        super.message = message;
        super.data = data;
    }


    public NeedRetryException(String msg) {
        super(msg);
    }

}
