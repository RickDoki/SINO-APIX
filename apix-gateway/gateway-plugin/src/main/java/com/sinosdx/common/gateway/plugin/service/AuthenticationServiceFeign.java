package com.sinosdx.common.gateway.plugin.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 查询客户端的secret
     *
     * @param secretKey
     * @return
     */
    @GetMapping("/auth/token/secret")
    R<JSONObject> queryClientSecret(@RequestParam String secretKey);
}
