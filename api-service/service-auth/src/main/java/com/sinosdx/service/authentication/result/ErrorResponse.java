package com.sinosdx.service.authentication.result;

import org.springframework.http.HttpStatus;

/**
 * @author wendy
 * @date 2020/11/25
 */
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final Integer code;

    protected ErrorResponse(final String message, final Integer code, HttpStatus httpStatus) {
        this.message = message;
        this.code = code;
        this.status = httpStatus;
    }

    public static ErrorResponse of(final String message, final Integer errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
