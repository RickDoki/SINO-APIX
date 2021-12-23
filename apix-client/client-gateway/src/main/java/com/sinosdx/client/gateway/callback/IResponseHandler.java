package com.sinosdx.client.gateway.callback;


import com.sinosdx.client.gateway.dto.Request;
import com.sinosdx.client.gateway.dto.RespErrMsg;
import com.sinosdx.client.gateway.dto.Response;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public interface IResponseHandler {

    void onFailure(Request paramRequest, RespErrMsg paramRespErrMsg);

    void onResponse(Request paramRequest, Response paramResponse);
}
