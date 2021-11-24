package com.sinosdx.service.authentication.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2020/12/23
 */
@Data
public class LoginParam implements Serializable {
    private static final long serialVersionUID = -7956145823795079488L;

    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private String loginType;
    /**
     * 企业微信授权code
     */
    private String code;

    /**
     * 验证码code
     */
    private String verifyCode;
    private String uuid;
}
