package com.sinosdx.service.management.consumer;

import com.sinosdx.service.management.dao.entity.OauthClientDetails;
import com.sinosdx.service.management.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2020/12/3
 */
@FeignClient("service-auth")
public interface OauthClientDetailsServiceFeign {

    /**
     * 校验token
     *
     * @param oauthClientDetail
     * @return
     */
    @PostMapping("/auth/oauth_client/create")
    R<Object> createOauthClientDetail(@RequestBody OauthClientDetails oauthClientDetail);

    /**
     * 删除认证客户端
     *
     * @param appCode
     * @return
     */
    @DeleteMapping("/auth/oauth_client/delete/{appCode}")
    R<Object> deleteOAuthClientDetail(@PathVariable("appCode") String appCode);

    /**
     * 查询client信息
     *
     * @param clientId
     * @return
     */
    @GetMapping("/auth/oauth_client/{clientId}")
    R<OauthClientDetails> queryByClientId(@PathVariable("clientId") String clientId);
}
