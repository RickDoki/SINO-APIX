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
package com.sinosdx.common.gateway.enums;

import com.sinosdx.common.base.annotation.GlobalExpEnumType;
import com.sinosdx.common.base.result.enums.BaseEnum;
import com.sinosdx.common.base.result.enums.ExpEnumCodeUtil;

/**
 * @author pengjiahu
 * @date 2021-06-10 00:40
 * @description
 */
@GlobalExpEnumType(module = 10, kind = 90)
public enum ResultEnum implements BaseEnum<Integer, String> {

    /**
     * 当前请求参数为空或数据缺失
     */
    REQUEST_EMPTY(1, "当前请求参数为空或数据缺失"),
    /**
     * 黑名单拒绝请求
     */
    BLACKLIST_IP(10, "The current IP [%s] is in the blacklist, reject request"),
    /**
     * 非白名单拒绝请求
     */
    WHITELIST_IP(11, "The current IP [%s] is not in the whitelist, reject the request"),
    /**
     * 非法请求
     */
    UN_SAFE_REQUEST(110, "非法请求，请稍后重试");

    private final Integer code;

    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getDesc() {
        return this.message;
    }

}
