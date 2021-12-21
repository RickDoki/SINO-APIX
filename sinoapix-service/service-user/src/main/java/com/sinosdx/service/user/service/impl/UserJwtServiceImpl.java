package com.sinosdx.service.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.consumer.IamServiceFeign;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.UserJwtService;
import com.sinosdx.service.user.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wendy
 * @date 2021/7/2
 */
@Service
@Slf4j
@RefreshScope
public class UserJwtServiceImpl implements UserJwtService {

    @Value("${jwt.secretKey:this_is_so_secret}")
    private String jwtSecretKey;

    @Value("${jwt.timeout:32400000}")
    private long jwtTimeoutMs;

    @Value("${jwt.expiredTime:28800}")
    private Long jwtExpiredTime;

    @Autowired
    private IamServiceFeign iamService;

    /**
     * 获取csp2.0 JWT
     *
     * @param iamUserToken
     * @param userId
     * @return
     */
    @Override
    public R<String> getJwt(String iamUserToken, String userId) {
        log.info("开始获取iam二级用户信息");
        R<JSONObject> userInfoR = iamService.querySecondUserInfoFromToken(null, iamUserToken);
        if (userInfoR.notSuccess()) {
            return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
        }

        JSONObject userInfo = userInfoR.getData();
        String mobile = userInfo.getString("mobile");
        String iamOpenid = userInfo.getString("iam_openid");
        String shortName = userInfo.getString("nickname");
        String email = userInfo.getString("userEmail");
        String username = userInfo.getString("username");

        Map<String, String> jwtMap = new HashMap<>();
        jwtMap.put("user_id", userId);
        jwtMap.put("phone_number", mobile);
        jwtMap.put("email", email);
        jwtMap.put("username", username);
        jwtMap.put("iam_openid", iamOpenid);
        jwtMap.put("short_name", shortName);
        jwtMap.put("user_shortname", shortName);
        jwtMap.put("user_token", iamUserToken);
        jwtMap.put("expired_at", String.valueOf(System.currentTimeMillis() / 1000));
        try {
            String jwt = JwtUtil.createJwt(jwtSecretKey, jwtMap, jwtTimeoutMs);
            return R.success(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(ResultCodeEnum.CREATE_JWT_ERROR);
        }
    }

    /**
     * 通过jwt获取用户信息
     *
     * @param jwt
     * @return
     */
    @Override
    public R<JSONObject> getUserInfoByJwt(String jwt) {
        Map<String, Claim> verifyJwt = JwtUtil.verifyJwt(jwtSecretKey, jwt);
        if (verifyJwt == null) {
            log.info("jwt解析失败");
            return R.fail(ResultCodeEnum.VERIFY_JWT_ERROR);
        }
        Long expiredAt = Long.valueOf(verifyJwt.get("expired_at").asString());
        if (expiredAt + jwtExpiredTime < System.currentTimeMillis() / 1000) {
            return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
        }
        String userId = verifyJwt.get("user_id").asString();
        String userToken = verifyJwt.get("user_token").asString();
        R<JSONObject> userInfoR = iamService.querySecondUserInfoFromToken(null, userToken);
        if (userInfoR.notSuccess()) {
            return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
        }
        JSONObject userInfo = userInfoR.getData();
        String mobile = userInfo.getString("mobile");
        String iamOpenid = userInfo.getString("iam_openid");
        String shortName = userInfo.getString("nickname");
        String email = userInfo.getString("userEmail");
        String username = userInfo.getString("username");

        JSONObject jwtJson = new JSONObject();
        jwtJson.put("id", userId);
        jwtJson.put("phone_number", mobile);
        jwtJson.put("email", email);
        jwtJson.put("username", username);
        jwtJson.put("iam_openid", iamOpenid);
        jwtJson.put("short_name", shortName);
        jwtJson.put("user_token", userToken);
        jwtJson.put("agreement", false);
        jwtJson.put("is_frozen", false);
        jwtJson.put("platform_role", "platform_member");
        jwtJson.put("last_active_time", System.currentTimeMillis());

        return R.success(jwtJson);
    }
}
