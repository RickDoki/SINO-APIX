package com.sinosdx.service.user.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5工具类
 *
 * @author wendy
 * @date 2020/12/10
 */
public class MD5Util {

    /**
     * 盐，用于混交md5
     */
    private static final String SALT = "&%5123***&&%%$$#@sinosdx";

    /**
     * 生成md5
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String base = str + "/" + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
