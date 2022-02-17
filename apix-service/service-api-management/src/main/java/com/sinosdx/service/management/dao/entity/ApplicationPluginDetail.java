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
package com.sinosdx.service.management.dao.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 服务插件详情（给订阅者展示）
 *
 * @author wendy
 * @date 2021/12/17
 */
@Data
@TableName("application_plugin_detail")
public class ApplicationPluginDetail implements Serializable {
    private static final long serialVersionUID = -713339860381531070L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 插件类型
     */
    private String pluginType;
    /**
     * 插件名称
     */
    private String name;
    /**
     * 插件介绍
     */
    private String description;
    /**
     * token请求地址
     */
    @TableField(exist = false)
    private String requestUrl;
    /**
     * token请求方式
     */
    @TableField(exist = false)
    private String requestType;
    /**
     * token请求参数
     */
    @TableField(exist = false)
    private String requestParam;
    /**
     * token请求返回值
     */
    @TableField(exist = false)
    private String response;
    /**
     * OAuth2 client_id
     */
    @TableField(exist = false)
    private String clientId;
    /**
     * OAuth2 client_secret
     */
    @TableField(exist = false)
    private String clientSecret;
    /**
     * OAuth2 客户端认证
     */
    @TableField(exist = false)
    private Boolean clientCredential;
    /**
     * token过期时间
     */
    @TableField(exist = false)
    private String tokenExpiration;
    /**
     * refresh token过期时间
     */
    @TableField(exist = false)
    private String refreshTokenExpiration;
    /**
     * jwt 请求头
     */
    @TableField(exist = false)
    private String header;
    /**
     * jwt 校验key
     */
    @TableField(exist = false)
    private String claimKey;
    /**
     * jwt secret key
     */
    @TableField(exist = false)
    private String claimValue;
    /**
     * basic auth username
     */
    @TableField(exist = false)
    private String username;
    /**
     * basic auth password
     */
    @TableField(exist = false)
    private String password;
    /**
     * cors allowOrigins
     */
    @TableField(exist = false)
    private String allowOrigins;
    /**
     * cors allowHeaders
     */
    @TableField(exist = false)
    private String allowHeaders;
    /**
     * cors exposeHeaders
     */
    @TableField(exist = false)
    private String exposeHeaders;
    /**
     * cors allowMethods
     */
    @TableField(exist = false)
    private String allowMethods;
    /**
     * cors allowCredentials
     */
    @TableField(exist = false)
    private String allowCredentials;
    /**
     * cors maxAge
     */
    @TableField(exist = false)
    private String maxAge;
    /**
     * 黑名单列表
     */
    @TableField(exist = false)
    private String blackListIp;
    /**
     * 白名单列表
     */
    @TableField(exist = false)
    private String whiteListIp;
    /**
     * sign secretKey
     */
    @TableField(exist = false)
    private String secretKey;
    /**
     * sentinel 服务控制时长
     */
    @TableField(exist = false)
    private String sentinelInterval;
    /**
     * sentinel 服务控流值
     */
    @TableField(exist = false)
    private Integer sentinelCount;
    /**
     * sentinel api控流配置
     */
    @TableField(exist = false)
    private List<JSONObject> sentinelApiConfig;

}
