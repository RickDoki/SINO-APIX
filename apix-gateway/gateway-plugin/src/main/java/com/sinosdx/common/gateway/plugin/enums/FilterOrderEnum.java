package com.sinosdx.common.gateway.plugin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengjiahu
 * @date 2021-12-30 16:09
 * @description
 */
@AllArgsConstructor
@Getter
public enum FilterOrderEnum {
    /**
     * global
     */
    G_DEFAULT(-51),
    G_BASE(-100),
    G_CACHE_POST_BODY(-99),
    G_REQUEST_LOG(-98),
    G_REQUEST_PRINT(-97),
    G_SAFE_CONTROL(-96),
    G_AFTER(-95),
    G_GRAY_LOAD_BALANCER(10250),
    /**
     * custom
     */
    C_BLACK_LIST_IP(-50),
    C_WHITE_LIST_IP(-49),
    C_CORS(-48),
    C_AUTHORIZE(-47),
    C_OAUTH(-46),
    C_JWT(-45),
    C_SIGN(-44),
    C_ERROR_LOG(-43),
    C_GZIP(-42),
    C_HTTP_LOG(-41),
    C_PROXY_CACHE(-40),
    C_REAL_IP(-39),
    C_REPLAY_ATTACKS(-38),
    ;

    private final int order;
}
