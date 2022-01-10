package com.sinosdx.service.authentication.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.controller.dto.GenerateTokenDto;
import com.sinosdx.service.authentication.dao.entity.ClientAppSecret;

/**
 * @author wendy
 * @date 2022/1/5
 */
public interface TokenService {

    /**
     * 给订阅方生成jwt
     *
     * @param generateTokenDto
     * @return
     */
    R<Object> generateJwt(GenerateTokenDto generateTokenDto);

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    R<Object> saveClientAppSecretKey(ClientAppSecret secret);

    /**
     * 查询客户端的secret
     *
     * @param secretKey
     * @return
     */
    R<ClientAppSecret> queryBySecret(String secretKey);

    /**
     * 给订阅方生成basic_token
     *
     * @param generateTokenDto
     * @return
     */
    R<Object> generateBasicToken(GenerateTokenDto generateTokenDto);
}
