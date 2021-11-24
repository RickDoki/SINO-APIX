package com.sinosdx.client.gateway.http;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public enum RespErrCode {
    AUTH_INVALID_TOKEN,
    AUTH_TOKEN_EXPIRED,
    AUTH_INVALID_ACCOUNT_OR_PSW,
    AUTH_INVALID_IP,
    AUTH_INVALID_DOMAIN,
    ACL_PERMISSION_DENIED,
    ACL_BLACKLIST,
    INVALID_API,
    FLOW_LIMITED,
    INVALID_PARAMETER,
    DEST_SERVICE_UNAVAILABLE,
    DEST_SERVICE_RESP_TIMEOUT,
    GATEWAY_RESP_TIMEOUT,
    GATEWAY_UNAVAILABLE,
    CMPT_EXECUTE_TIMEOUT,
    ConnectTimeoutException,
    SocketTimeoutException,
    UNKNOWN_ERR;
}


