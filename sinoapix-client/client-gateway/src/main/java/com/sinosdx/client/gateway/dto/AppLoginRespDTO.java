package com.sinosdx.client.gateway.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Setter
@Getter
public class AppLoginRespDTO {

    private Boolean result;
    private String token;
    private RespErrMsg respErrMsg;
}
