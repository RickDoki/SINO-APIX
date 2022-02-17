/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
