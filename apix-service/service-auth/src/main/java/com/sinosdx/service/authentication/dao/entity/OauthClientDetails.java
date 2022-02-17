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
package com.sinosdx.service.authentication.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2020/11/29
 */
@TableName(value = "oauth_client_details")
@Data
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = -8107346604495565635L;

    /**
     * 客户端ID，主要用于标识对应的应用
     */
    @TableId(value = "client_id", type = IdType.INPUT)
    private String clientId;

    private String resourceIds;

    /**
     * 客户端秘钥，BCryptPasswordEncoder加密算法加密
     */
    private String clientSecret;

    /**
     * 对应的范围
     */
    private String scope;

    /**
     * 认证模式
     */
    private String authorizedGrantTypes;

    /**
     * 认证后重定向地址
     */
    private String webServerRedirectUri;

    private String authorities;

    /**
     * 令牌有效期
     */
    private Integer accessTokenValidity;

    /**
     * 令牌刷新周期
     */
    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}


