package com.sinosdx.service.user.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;

/**
 * @author wendy
 * @date 2021/7/1
 */
public interface UserJwtService {

    /**
     * 获取csp2.0 JWT
     *
     * @param iamUserToken
     * @param userId soc user uuid
     * @return
     */
    R<String> getJwt(String iamUserToken, String userId);

    /**
     * 通过jwt获取用户信息
     *
     * @param jwt
     * @return
     */
    R<JSONObject> getUserInfoByJwt(String jwt);
}
