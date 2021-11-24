package com.sinosdx.service.authentication.service.impl;

import com.sinosdx.service.authentication.consumer.ApplicationServiceFeign;
import com.sinosdx.service.authentication.consumer.HandleLogServiceFeign;
import com.sinosdx.service.authentication.controller.vo.TokenInfo;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.TokenVerifyService;
import com.sinosdx.starter.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/3
 */
@Slf4j
@Service
public class TokenVerifyServiceImpl implements TokenVerifyService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ApplicationServiceFeign applicationService;

    @Autowired
    private HandleLogServiceFeign handleLogService;

    /**
     * 校验token
     *
     * @param params
     * @return
     */
    @Override
    public R<Object> tokenVerify(Map<String, String> params) {
        if (!params.containsKey("accessToken")) {
            return R.fail(ResultCodeEnum.TOKEN_ERROR);
        }
        String accessToken = params.get("accessToken");
        final Claims claims = getClaimsFromToken(accessToken);
        if (null == claims || !claims.containsKey("client_id")) {
            return R.fail(ResultCodeEnum.TOKEN_ERROR);
        }

        // 校验过期时间
        try {
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return R.fail(ResultCodeEnum.TOKEN_EXPIRED);
            }
        } catch (Exception e) {
            return R.fail(ResultCodeEnum.TOKEN_EXPIRED);
        }

        // 校验token是否已注销
        if (!redisService.hasKey("access:" + accessToken) || !redisService.hasKey("client_id_to_access:" + claims.get("client_id"))) {
            return R.fail(ResultCodeEnum.TOKEN_NULL);
        }

        log.info("--------------------------- userId: " + claims.get("userId"));
        // 导入应用调用日志埋点（只保存客户端认证后的调用日志）
        if (null == claims.get("userId")) {
            String uri = params.getOrDefault("uri", null);
//            createServiceLog(claims, uri);
        }

        // 返回用户信息
        return R.success();
    }

    /**
     * 解析token
     *
     * @param params
     * @return
     */
    @Override
    public R<TokenInfo> convertToken(Map<String, String> params) {
        if (!params.containsKey("accessToken")) {
            return R.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        String accessToken = params.get("accessToken");
        final Claims claims = getClaimsFromToken(accessToken);
        if (null == claims) {
            return R.fail(ResultCodeEnum.TOKEN_CONVERT_ERROR);
        }

        // 校验token是否已注销
        if (!redisService.hasKey("access:" + accessToken)) {
            return R.fail(ResultCodeEnum.TOKEN_CONVERT_EXPIRED);
        }

        TokenInfo tokenInfo = new TokenInfo();

        // 校验过期时间
        try {
            tokenInfo.setExpireTime(claims.getExpiration().getTime());
        } catch (Exception e) {
            return R.fail(ResultCodeEnum.TOKEN_CONVERT_ERROR);
        }

        if (claims.containsKey("phone")) {
            tokenInfo.setPhone(claims.get("phone").toString());
        }
        if (claims.containsKey("userId") && Integer.parseInt(claims.get("userId").toString()) == 0) {
            tokenInfo.setUserId(Integer.valueOf(claims.get("userId").toString()));
        }
        if (claims.containsKey("email")) {
            tokenInfo.setEmail(claims.get("email").toString());
        }
        if (claims.containsKey("client_id")) {
            tokenInfo.setClientId(claims.get("client_id").toString());
        }
        if (claims.containsKey("username")) {
            tokenInfo.setUsername(claims.get("username").toString());
        }

        // 返回用户信息
        return R.success(tokenInfo);
    }

    /**
     * get claims from token
     *
     * @author wmlucas.cn@gmail.com
     * @date 2019-08-06
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("sinosdx".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 导入应用调用埋点
     *
     * @param claims
     * @param uri
     */
    private void createServiceLog(Claims claims, String uri) {
        String appCode = claims.get("client_id", String.class).split("-")[1];
        Map<String, Object> apiLog = new HashMap<>();
        apiLog.put("startTime", System.currentTimeMillis());
        apiLog.put("appCode", appCode);
        apiLog.put("uri", uri);
        apiLog.put("serviceName", applicationService.queryAppByAppCode(appCode).getData().getName());
        log.info("导入应用埋点：{}", apiLog);
        try {
            handleLogService.saveApiLog(apiLog);
        } catch (Exception e) {
            log.error("导入应用埋点出错", e);
        }
    }
}
