package com.sinosdx.service.event.bridge.service.bo;

import lombok.Data;

/**
 * @author pengjiahu
 * @date 2021-01-18 13:22
 * @description
 */
@Data
public class PlatformApiBO {

    private String name;
    private String method;
    private String type;
    private String url;
}
