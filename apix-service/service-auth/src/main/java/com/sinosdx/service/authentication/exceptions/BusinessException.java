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
package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.BaseEnum;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 业务异常类
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public BusinessException() {
        super();
    }

    public BusinessException(Object data) {
        super.data = data;
    }

    public BusinessException(BaseEnum baseEnum) {
        super.code = baseEnum.getCode().toString();
        super.message = baseEnum.getDesc();
    }

    public BusinessException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public BusinessException(Integer code, String msg) {
        super.code = String.valueOf(code);
        super.message = msg;
    }

    public BusinessException(ResultCodeEnum resultCode, String msg) {
        super.code = String.valueOf(resultCode.getCode());
        super.message = msg + resultCode.getDesc();
    }

    public BusinessException(String msg) {
        super(msg);
    }


    public static BusinessException wrap() {
        return new BusinessException();
    }

    public static BusinessException wrap(Object data) {
        return new BusinessException(data);
    }

    public static BusinessException wrap(BaseEnum baseEnum) {
        return new BusinessException(baseEnum);
    }

    public static BusinessException wrap(ResultCodeEnum resultCode, Object data) {
        return new BusinessException(resultCode, data);
    }

    public static BusinessException wrap(Integer code, String msg) {
        return new BusinessException(code, msg);
    }

    public static BusinessException wrap(ResultCodeEnum resultCode, String msg) {
        return new BusinessException(resultCode, msg);
    }

    public static BusinessException wrap(String msg) {
        return new BusinessException(msg);
    }

}
