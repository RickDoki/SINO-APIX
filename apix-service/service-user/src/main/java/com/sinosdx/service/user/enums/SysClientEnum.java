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
package com.sinosdx.service.user.enums;

import lombok.Getter;

/**
 * @author wendy
 * @date 2021/12/20
 */
@Getter
public enum SysClientEnum {

    /**
     * 资源类型
     */
    USER("user", "用户");

    private final String name;
    private final String desc;

    SysClientEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
