package com.sinosdx.common.gateway.plugin.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author pengjiahu
 * @date 2022-01-05 12:26
 * @description
 */
@Data
@Builder
public class ResponseInfo {

    private ServerWebExchange exchange;
    private String headers;
    private Integer statusCode;
    private String result;
}
