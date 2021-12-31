package com.sinosdx.common.gateway.plugin.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author pengjiahu
 * @date 2021-12-31 15:53
 * @description
 */
@Data
@Builder
public class ResponseData {

    private Object o;

    private ServerWebExchange exchange;

}
