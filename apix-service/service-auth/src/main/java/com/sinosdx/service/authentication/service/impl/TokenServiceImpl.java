package com.sinosdx.service.authentication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.toolkit.auth.JwtUtil;
import com.sinosdx.service.authentication.consumer.ApplicationServiceFeign;
import com.sinosdx.service.authentication.controller.dto.GenerateTokenDto;
import com.sinosdx.service.authentication.dao.entity.ClientAppSecret;
import com.sinosdx.service.authentication.dao.mapper.ClientAppSecretMapper;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author wendy
 * @date 2022/1/5
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Resource
    private ClientAppSecretMapper clientAppSecretMapper;

    @Autowired
    private ApplicationServiceFeign applicationService;

    /**
     * 给订阅方生成jwt
     *
     * @param generateTokenDto
     * @return
     */
    @Override
    public R<Object> generateJwt(GenerateTokenDto generateTokenDto) {
        if (StringUtils.isEmpty(generateTokenDto.getClaimValue())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        ClientAppSecret clientAppSecret = this.queryBySecret(generateTokenDto.getClaimValue()).getData();
        if (null == clientAppSecret) {
            return R.fail(ResultCodeEnum.PARAM_IS_INVALID);
        }
        Map<String, String> claimMap = new HashMap<>();
        claimMap.put("appCode", clientAppSecret.getAppCode());
        claimMap.put("clientId", String.valueOf(clientAppSecret.getClientId()));
        claimMap.put("userId", String.valueOf(clientAppSecret.getUserId()));

        com.sinosdx.service.authentication.result.R<JSONObject> objectR = applicationService.queryPluginConfigs("jwt", clientAppSecret.getAppCode());
        if (objectR.notSuccess()) {
            return R.fail(ResultCodeEnum.DATA_IS_WRONG);
        }
        JSONObject configJson = objectR.getData();
        Long expiration = configJson.getLong("TokenExpiration");
        String claimKey = configJson.getString("keyClaimName");
        claimMap.put(claimKey, generateTokenDto.getClaimValue());

        try {
            String jwt = JwtUtil.createJwt(null, claimMap, expiration);
            return R.success(Collections.singletonMap("jwt", jwt));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成jwt错误", e);
            return R.fail(ResultCodeEnum.CREATE_JWT_ERROR);
        }
    }

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    @Override
    public R<Object> saveClientAppSecretKey(ClientAppSecret secret) {
        ClientAppSecret clientAppSecret = clientAppSecretMapper.selectOne(new LambdaQueryWrapper<ClientAppSecret>()
                .eq(ClientAppSecret::getSecretKey, secret.getSecretKey()));
        if (null == clientAppSecret) {
            clientAppSecretMapper.insert(secret);
        }
        return R.success();
    }

    /**
     * 查询客户端的secret
     *
     * @param secretKey
     * @return
     */
    @Override
    public R<ClientAppSecret> queryBySecret(String secretKey) {
        ClientAppSecret clientAppSecret = clientAppSecretMapper.selectOne(new LambdaQueryWrapper<ClientAppSecret>()
                .eq(ClientAppSecret::getSecretKey, secretKey));
        return R.success(clientAppSecret);
    }

    /**
     * 给订阅方生成basic_token
     *
     * @param generateTokenDto
     * @return
     */
    @Override
    public R<Object> generateBasicToken(GenerateTokenDto generateTokenDto) {
        if (StringUtils.isAnyEmpty(generateTokenDto.getUsername(), generateTokenDto.getPassword())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        ClientAppSecret clientAppSecret = this.queryBySecret(generateTokenDto.getPassword()).getData();
        if (null == clientAppSecret) {
            return R.fail(ResultCodeEnum.PARAM_IS_INVALID);
        }

        String token = new String(
                Base64.getEncoder().encode(
                        String.format("%s:%s", generateTokenDto.getUsername(), generateTokenDto.getPassword())
                                .getBytes(StandardCharsets.UTF_8)));
        return R.success(Collections.singletonMap("token", token));
    }

    /**
     * 查询客户端的secret
     *
     * @param appCode
     * @return
     */
    @Override
    public R<List<ClientAppSecret>> querySecretByAppCode(String appCode) {
        List<ClientAppSecret> clientAppSecrets = clientAppSecretMapper.selectList(new LambdaQueryWrapper<ClientAppSecret>()
                .eq(ClientAppSecret::getAppCode, appCode));
        return R.success(clientAppSecrets);
    }

    /**
     * 删除secret
     *
     * @param appCode
     * @return
     */
    @Override
    public R<Object> deleteClientAppSecret(String appCode) {
        clientAppSecretMapper.delete(new LambdaQueryWrapper<ClientAppSecret>()
                .eq(ClientAppSecret::getAppCode, appCode));
        return R.success();
    }

}
