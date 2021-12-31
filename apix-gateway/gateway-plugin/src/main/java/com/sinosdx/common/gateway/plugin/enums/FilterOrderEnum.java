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
     * base
     */
    BASE(-100),
    CACHE_POST_BODY(-98),
    GRAY_LOAD_BALANCER(10250),
    REQUEST_LOG(-97),
    REQUEST_PRINT(-99),
    SAFE_CONTROL(-99);

    private final int order;
}
