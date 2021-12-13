package com.sinosdx.service.user.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;

/**
 * @author wendy
 * @date 2021/6/15
 */
public interface UserDigestService {

    /**
     * 获取用户digestKey
     *
     * @param jwt
     * @param userData
     * @return
     */
    R<String> getUserDigestKey(String jwt, JSONObject userData);

    /**
     * 根据digest获取用户信息
     *
     * @param digestKey
     * @return
     */
    R<JSONObject> getUserDigestData(String digestKey);

    /**
     * 获取区域和用户信息
     *
     * @param region
     * @param serviceKey
     * @param digestKey
     * @return
     */
    R<JSONObject> getRegionAndUserInfo(String region, String serviceKey, String digestKey);

    /**
     * 区分c01和csp2.0请求获取digestKey
     *
     * @param jwt
     * @param encrypt
     * @return
     */
    R<Object> getDigestKeyForProxyGateway(String jwt, String encrypt);
}
