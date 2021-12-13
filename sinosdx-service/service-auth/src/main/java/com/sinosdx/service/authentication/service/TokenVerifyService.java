package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.controller.vo.TokenInfo;
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/3
 */
@RestController
public interface TokenVerifyService {

    /**
     * 校验token
     *
     * @param params
     * @return
     */
    @PostMapping("/auth/token/verify")
    R<Object> tokenVerify(@RequestBody Map<String, String> params);

    /**
     * 解析token
     *
     * @param params
     * @return
     */
    @PostMapping("/auth/token/convert")
    R<TokenInfo> convertToken(@RequestBody Map<String, String> params);
}
