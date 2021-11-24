package com.sinosdx.common.gateway.entity;

import lombok.Data;

/**
 * 过滤排除地址(通用)
 *
 * @author pengjiahu
 * @date 2021-07-14 17:42
 * @description
 */
@Data
public class BaseConfig {

    /**
     * 需要过滤的地址（多个;号隔开）
     */
    private String authExcludeUri;
}
