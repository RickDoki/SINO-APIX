package com.sinosdx.service.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.consumer.IamServiceFeign;
import com.sinosdx.service.user.dao.mapper.UserDigestMapper;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.SocService;
import com.sinosdx.service.user.service.UserDigestService;
import com.sinosdx.service.user.utils.AESUtil;
import com.sinosdx.service.user.utils.JwtUtil;
import com.sinosdx.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author wendy
 * @date 2021/6/15
 */
@Slf4j
@Service
public class UserDigestServiceImpl implements UserDigestService {

    @Resource
    private UserDigestMapper userDigestMapper;

    @Autowired
    private IamServiceFeign iamService;

    @Autowired
    private SocService socService;

    @Autowired
    private RedisService redisService;

    @Value("${jwt.secretKey:this_is_so_secret}")
    private String jwtSecretKey;

    private static final Object LOCK = new Object();

    /**
     * 获取用户digestKey
     *
     * @param jwt
     * @param userData {
     *                 "email": "",
     *                 "open_id": "4779883670321588",
     *                 "tenant_id": "800d2894-f881-4ebf-9fe1-71cba84cbe64",
     *                 "tenant_name": "devops开发",
     *                 "tenant_short_name": "idcdevops",
     *                 "user_id": "d4595dc9-ad27-47b2-9724-3b130fffa921",
     *                 "user_mobile": "18602486892",
     *                 "user_token": "13df8133ccd54ddc841f90ba4a5dab3b",
     *                 "username": "18602486892"
     *                 }
     * @return
     */
    @Override
    public R<String> getUserDigestKey(String jwt, JSONObject userData) {
        if (userData.containsKey("encrypt") && StrUtil.isNotEmpty(userData.getString("encrypt"))) {
            String decrypt = AESUtil.decryptAES(userData.getString("encrypt"));
            userData = JSON.parseObject(decrypt);
        } else {
            if (!userData.containsKey("user_mobile")) {
                return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
            }

//            String mobile = userData.getString("user_mobile");
//            JSONObject reqJson = new JSONObject();
//            reqJson.put("mobile", mobile);
//            reqJson.put("jwt", jwt);
//            JSONObject userTokenJson = iamService.getUserTokenByMobile(reqJson).getData();
//            String userToken = userTokenJson.getString("userToken");
//            String openid = userTokenJson.getString("openid");
//            if (StringUtils.isAnyEmpty(userToken, openid)) {
//                return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
//            }

            try {
                Map<String, Claim> claimMap = JwtUtil.verifyJwt(jwtSecretKey, jwt);
                if (null == claimMap) {
                    return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
                }
                userData.put("open_id", claimMap.get("iam_openid").asString());
                userData.put("user_token", claimMap.get("user_token").asString());
            } catch (Exception e) {
                e.printStackTrace();
                return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
            }
        }

        String digestKey = "csp2" + DigestUtils.md5DigestAsHex(JSON.toJSONBytes(userData));
        synchronized (LOCK) {
//            UserDigest userDigest = userDigestMapper.selectById(digestKey);
//            if (null == userDigest) {
            //                userDigest = UserDigest.builder()
            //                        .digestKey(digestKey)
            //                        .data(userData.toJSONString())
            //                        .mobile(userData.getString("user_mobile"))
            //                        .build();
            //                userDigestMapper.insert(userDigest);
            //            }
            String userDigest = redisService.get(Constants.REDIS_PREFIX_DIGEST_KEY + digestKey);
            if (StringUtils.isEmpty(userDigest)) {
                redisService.set(Constants.REDIS_PREFIX_DIGEST_KEY + digestKey,
                        userData.toJSONString(),
                        Constants.REDIS_EXPIRED_TIME_DIGEST_KEY);
            }
        }
        return R.success(digestKey);
    }

    /**
     * 根据digest获取用户信息
     *
     * @param digestKey
     * @return
     */
    @Override
    public R<JSONObject> getUserDigestData(String digestKey) {
//        UserDigest userDigest = userDigestMapper.selectById(digestKey);
        String userDigest = redisService.get(Constants.REDIS_PREFIX_DIGEST_KEY + digestKey);
//        if (null == userDigest) {
        if (StringUtils.isEmpty(userDigest)) {
            return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
        }
        return R.success(JSON.parseObject(userDigest));
    }

    /**
     * 获取区域和用户信息
     *
     * @param region
     * @param serviceKey
     * @param digestKey
     * @return
     */
    @Override
    public R<JSONObject> getRegionAndUserInfo(String region, String serviceKey, String digestKey) {
//        UserDigest userDigest = userDigestMapper.selectById(digestKey);
        String userDigest = redisService.get(Constants.REDIS_PREFIX_DIGEST_KEY + digestKey);
//        if (null == userDigest) {
        if (StringUtils.isEmpty(userDigest)) {
            return R.fail(ResultCodeEnum.GET_USER_TOKEN_ERROR);
        }

        JSONObject userInfo = JSON.parseObject(userDigest);

        R<Object> regionListR = socService.getRegionList(region, serviceKey);

        JSONObject resultJson = new JSONObject();
        resultJson.put("tenantInfo", userInfo);
        resultJson.put("serviceRegionResp", regionListR.getData());
        return R.success(resultJson);
    }

    /**
     * 区分c01和csp2.0请求获取digestKey
     *
     * @param jwt
     * @param encrypt
     * @return
     */
    @Override
    public R<Object> getDigestKeyForProxyGateway(String jwt, String encrypt) {
        String decrypt = AESUtil.decryptAES(encrypt);
        JSONObject userJson = JSON.parseObject(decrypt);
        String tenantId = userJson.getString("tenant_id");
        String digestKey = null;
        JSONObject encryptJson = new JSONObject();
        encryptJson.put("encrypt", encrypt);
        if (tenantId.toLowerCase().startsWith("csp2")) {
            digestKey = getUserDigestKey(jwt, encryptJson).getData();
        } else {
            encryptJson.put("jwt", jwt);
            digestKey = iamService.getDigestKeyByJwtAndEncrypt(encryptJson);
        }

        String result = null;
        if (null == digestKey) {
            result = "{\"code\": \"822001\",\"data\": " + digestKey + ",\"msg\": \"请重新登录\"}";
        } else {
            result = "{\"code\": \"10004\",\"data\": \"" + digestKey + "\",\"msg\": \"success\"}";
        }

        return R.success(result);
    }
}
