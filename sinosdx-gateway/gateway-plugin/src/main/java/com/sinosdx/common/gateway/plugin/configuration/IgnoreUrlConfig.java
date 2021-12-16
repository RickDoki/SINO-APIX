package com.sinosdx.common.gateway.plugin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengjiahu
 * @date 2021-06-15 17:25
 * @description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = IgnoreUrlConfig.PREFIX)
public class IgnoreUrlConfig {

    public static final String PREFIX = "mt.gateway";

    private List<String> ignoreUrls = new ArrayList<>();

    /**
     * 判断URL 是否自动跳过
     *
     * @param url url
     * @return 自动跳过
     */
    public boolean isIgnoreUrl(String url) {
        return ignoreUrls.stream().anyMatch(url::contains);
    }
}
