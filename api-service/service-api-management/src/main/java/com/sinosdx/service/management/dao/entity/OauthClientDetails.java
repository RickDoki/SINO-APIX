package com.sinosdx.service.management.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2020/11/29
 */
@Data
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 3841067903193013529L;

    /**
     * 客户端ID，主要用于标识对应的应用
     */
    private String clientId;

    private String resourceIds;

    /**
     * 客户端秘钥，BCryptPasswordEncoder加密算法加密
     */
    private String clientSecret;

    /**
     * 对应的范围
     */
    private String scope;

    /**
     * 认证模式
     */
    private String authorizedGrantTypes;

    /**
     * 认证后重定向地址
     */
    private String webServerRedirectUri;

    private String authorities;

    /**
     * 令牌有效期
     */
    private Integer accessTokenValidity;

    /**
     * 令牌刷新周期
     */
    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}


