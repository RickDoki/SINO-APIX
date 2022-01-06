package com.sinosdx.service.authentication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<ClientAppSecret> secretList = clientAppSecretMapper.selectList(new LambdaQueryWrapper<ClientAppSecret>()
                .eq(ClientAppSecret::getSecretKey, generateTokenDto.getClaimValue()));
        if (secretList.isEmpty()) {
            return R.fail(ResultCodeEnum.PARAM_IS_INVALID);
        }
        ClientAppSecret clientAppSecret = secretList.get(0);
        Map<String, String> claimMap = new HashMap<>();
        claimMap.put("appCode", clientAppSecret.getAppCode());
        claimMap.put("clientId", String.valueOf(clientAppSecret.getClientId()));
        claimMap.put("userId", String.valueOf(clientAppSecret.getUserId()));

        com.sinosdx.service.authentication.result.R<JSONObject> objectR = applicationService.queryPluginConfigs("jwt", clientAppSecret.getAppCode());
        if (objectR.notSuccess()) {
            return R.fail(ResultCodeEnum.DATA_IS_WRONG);
        }
        JSONObject configJson = objectR.getData();
        Long expiration = configJson.getLong("expiration");
        String claimKey = configJson.getString("claimKey");
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
        clientAppSecretMapper.insert(secret);
        return R.success();
    }
}
