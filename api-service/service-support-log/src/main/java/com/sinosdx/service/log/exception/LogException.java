package com.sinosdx.service.log.exception;


import com.sinosdx.service.log.enums.ResultCodeEnum;

/**
 * @author: fanyi
 * @create: 2021-10-28 17:59
 * @description: no description
 */
public class LogException extends RuntimeException {
    private Integer code;

    public LogException(String message) {
        super(message);
        this.code = 500;
    }

    public LogException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public LogException(ResultCodeEnum codeEnum) {
        super(codeEnum.getDesc());
        this.code = codeEnum.getCode();
    }


    public Integer getCode() {
        return code;
    }
}
