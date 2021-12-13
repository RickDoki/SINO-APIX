package com.sinosdx.service.authentication.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wendy
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/auth/qywx")
public interface QywxService {

    /**
     * 获取企业微信accessToken
     *
     * @return
     */
    @GetMapping("/access_token")
    String getAccessToken();

    /**
     * 获取当前访问用户信息
     *
     * @param code
     * @return
     */
    @GetMapping("/login/user/info")
    R<JSONObject> getQywxUserInfo(String code);

}
