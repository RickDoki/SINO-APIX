package com.sinosdx.gateway.consumer;

import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-auth")
public interface AuthenticationServiceFeign {

    /**
     * 校验token
     *
     * @param params
     * @return
     */
    @PostMapping("/auth/token/verify")
    R<Object> tokenVerify(@RequestBody Map<String, String> params);
}
