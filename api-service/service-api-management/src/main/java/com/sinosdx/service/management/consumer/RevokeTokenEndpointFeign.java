package com.sinosdx.service.management.consumer;

import com.sinosdx.service.management.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @author wendy
 * @date 2021/1/7
 */
@FeignClient("service-auth")
public interface RevokeTokenEndpointFeign {

    /**
     * 注销token
     *
     * @param accessToken
     * @return
     */
    @DeleteMapping("/auth/logout")
    R<Object> revokeToken(String accessToken);
}
