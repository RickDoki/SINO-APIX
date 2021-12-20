package com.sinosdx.client.gateway.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public enum HttpMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private static final Map<String, HttpMethod> mappings;

    static {
        mappings = new HashMap<>(8);

        for (HttpMethod httpMethod : values()) {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }

    public static HttpMethod resolve(String method) {
        return (method != null) ? mappings.get(method) : null;
    }

    public boolean matches(String method) {
        return (this == resolve(method));
    }
}


