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
