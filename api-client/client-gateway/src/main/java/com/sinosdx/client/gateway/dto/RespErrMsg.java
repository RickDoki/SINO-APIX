package com.sinosdx.client.gateway.dto;

import com.sinosdx.client.gateway.http.RespErrCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Setter
@Getter
public class RespErrMsg {

    private RespErrCode errCode;
    private String errMsg;

}



