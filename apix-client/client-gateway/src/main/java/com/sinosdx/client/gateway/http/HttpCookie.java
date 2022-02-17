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
package com.sinosdx.client.gateway.http;

import com.sinosdx.client.gateway.util.Assert;
import lombok.Getter;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Getter
public class HttpCookie {

    private final String name;
    private final String value;

    public HttpCookie(String name, String value) {
        Assert.hasLength(name, "'name' 必须不能为空.");
        this.name = name;
        this.value = (value != null) ? value : "";
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpCookie)) {
            return false;
        }
        HttpCookie otherCookie = (HttpCookie) other;
        return this.name.equalsIgnoreCase(otherCookie.getName());
    }

    @Override
    public String toString() {
        return this.name + '=' + this.value;
    }
}



