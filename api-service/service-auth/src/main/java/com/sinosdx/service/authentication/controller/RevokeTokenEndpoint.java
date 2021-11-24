package com.sinosdx.service.authentication.controller;

import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wendy
 * @date 2020/12/2
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/auth/logout")
    @ResponseBody
    public R<Object> revokeToken(String accessToken) {
        if (consumerTokenServices.revokeToken(accessToken)) {
            return R.success();
        } else {
            return R.fail(ResultCodeEnum.REVOKE_TOKEN_FAILED);
        }
    }
}

