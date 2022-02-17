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
package com.sinosdx.common.gateway.exception;

import com.sinosdx.common.base.exceptions.BaseException;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;

/**
 * @author pengjiahu
 * @date 2021-06-10 00:40
 * @description
 */
public class CustomException extends BaseException {

    private static final long serialVersionUID = 3723471287889297081L;

    public CustomException() {
        super();
    }

    public CustomException(Object data) {
        super();
        super.data = data;
    }

    public CustomException(ResultCodeEnum resultCode) {
        super(resultCode);
    }

    public CustomException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public CustomException(String msg) {
        super(msg);
    }
}
