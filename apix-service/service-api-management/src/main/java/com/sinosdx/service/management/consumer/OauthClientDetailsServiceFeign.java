/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
