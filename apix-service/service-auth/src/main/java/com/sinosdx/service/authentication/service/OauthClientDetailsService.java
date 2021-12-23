package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.dao.entity.OauthClientDetails;
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/3
 */
@RestController
public interface OauthClientDetailsService {

    /**
     * 创建新客户端（客户端认证使用）
     *
     * @param oauthClientDetail
     * @return
     */
    @PostMapping("/auth/oauth_client/create")
    R<Object> createOAuthClientDetail(@RequestBody OauthClientDetails oauthClientDetail);

    /**
     * 删除认证客户端
     *
     * @param appCode
     *
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
