package com.sinosdx.common.gateway.utils;

import com.sinosdx.common.gateway.properties.AuthConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author pengjiahu
 * @date 2021-06-24 17:13
 * @description
 */
public class RequestUtil {

    /**
     * 获得Token值
     *
     * @param request ServerHttpRequest
     * @return Token值
     */
    public static String extractToken(ServerHttpRequest request) {
        String authToken = request.getHeaders().getFirst(AuthConstant.AUTH_HEADER);
        if (StringUtils.isNotBlank(authToken)) {
            authToken = authToken.replace(AuthConstant.AUTH_HEADER_PREFIX, "").trim();
        }
        if (StringUtils.isBlank(authToken)) {
            String accessToken = request.getQueryParams().getFirst(AuthConstant.AUTH_ACCESS_TOKEN);
            if (accessToken != null) {
                authToken = accessToken.trim();
            }
        }
        return authToken;
    }

}
