package com.sinosdx.common.gateway.interception;

import com.sinosdx.common.base.constants.HeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2021-07-14 15:06
 * @description
 */
@Slf4j
public class FeignClientInterception implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(HeaderConstant.FEIGN_TOKEN_HEADER, "todo:service room certification");
    }
}
