package com.sinosdx.service.authentication.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2021/1/7
 */
@Data
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 321645683581681825L;

    private Long expireTime;
    private String clientId;
    private String phone;
    private String email;
    private String username;
    private Integer userId;
}
