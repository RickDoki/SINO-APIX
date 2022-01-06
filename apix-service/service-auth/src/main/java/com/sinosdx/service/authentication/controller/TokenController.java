package com.sinosdx.service.authentication.controller;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.controller.dto.GenerateTokenDto;
import com.sinosdx.service.authentication.dao.entity.ClientAppSecret;
import com.sinosdx.service.authentication.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wendy
 * @date 2022/1/6
 */
@RestController
@RequestMapping("/auth/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    @PostMapping("/secretKey")
    public R<Object> saveClientAppSecretKey(@RequestBody ClientAppSecret secret) {
        return tokenService.saveClientAppSecretKey(secret);
    }

    /**
     * 给订阅方生成jwt
     *
     * @param generateTokenDto
     * @return
     */
    @PostMapping("/jwt")
    public R<Object> generateJwt(@RequestBody GenerateTokenDto generateTokenDto) {
        return tokenService.generateJwt(generateTokenDto);
    }
}
