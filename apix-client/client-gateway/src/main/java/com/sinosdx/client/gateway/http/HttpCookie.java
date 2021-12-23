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



