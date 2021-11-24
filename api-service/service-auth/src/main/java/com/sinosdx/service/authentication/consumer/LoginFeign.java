package com.sinosdx.service.authentication.consumer;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-auth")
public interface LoginFeign {

    /**
     * 认证服务获取token
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/oauth/token")
    JSONObject requestToken(@RequestParam Map<String, String> paramMap);
}
