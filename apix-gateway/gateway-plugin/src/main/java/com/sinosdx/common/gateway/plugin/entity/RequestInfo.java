package com.sinosdx.common.gateway.plugin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author pengjiahu
 * @date 2021-12-30 14:57
 * @description
 */
@Data
@Builder
@ToString
public class RequestInfo {

    /**
     * 请求唯一标识
     */
    private String requestNo;
    /**
     * 真实ip
     */
    private String ip;
    /**
     * 应用唯一标识code
     */
    private String appCode;
    /**
     * 请求路径
     */
    private String requestPath;

}
