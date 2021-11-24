package com.sinosdx.service.user.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
public class AESUtil {
    private static String DEFAULTKEY = "QAZWSXEDC2019VBN";  //16位
    private static String DEFAULTIV = "2019VBNQWERTYUIO";  //16位

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String decryptAES(String data) {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keyspec = new SecretKeySpec(DEFAULTKEY.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(DEFAULTIV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original).trim();
        } catch (Exception e) {
            log.error("decryptAES解密异常,返回null");
            return null;
        }
    }
}
