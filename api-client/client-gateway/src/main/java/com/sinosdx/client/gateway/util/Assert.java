package com.sinosdx.client.gateway.util;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public class Assert {

    public static void hasLength(String text, String message) {
        if (!hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }
}


