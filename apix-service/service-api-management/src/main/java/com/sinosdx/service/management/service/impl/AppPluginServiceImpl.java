package com.sinosdx.service.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.consumer.OauthClientDetailsServiceFeign;
import com.sinosdx.service.management.consumer.SysUserServiceFeign;
import com.sinosdx.service.management.consumer.TokenServiceFeign;
import com.sinosdx.service.management.dao.entity.*;
import com.sinosdx.service.management.dao.mapper.ApplicationMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationPluginClientMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationPluginMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationSubscribeMapper;
import com.sinosdx.service.management.enums.PluginTypeEnum;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.AppPluginService;
import com.sinosdx.service.management.service.ApplicationService;
import com.sinosdx.service.management.utils.MD5Util;
import com.sinosdx.service.management.utils.ThreadContext;
import com.sinosdx.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author wendy
 * @date 2022/1/12
 */
@Service
@Slf4j
public class AppPluginServiceImpl implements AppPluginService {

    @Resource
    private ApplicationPluginMapper applicationPluginMapper;

    @Resource
    private ApplicationMapper applicationMapper;

    @Autowired
    private RedisService redisService;

    @Resource
    private ApplicationPluginClientMapper applicationPluginClientMapper;

    @Autowired
    private OauthClientDetailsServiceFeign oauthClientDetailsService;

    @Autowired
    private TokenServiceFeign tokenService;

    @Resource
    private ApplicationSubscribeMapper applicationSubscribeMapper;

    @Autowired
    private SysUserServiceFeign sysUserService;

    @Autowired
    private ApplicationService applicationService;

    /**
     * 服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    @Override
    @Transactional
    public R<Object> addAppPlugin(ApplicationPlugin applicationPlugin) {
        if (StringUtils.isAnyEmpty(applicationPlugin.getPluginType(), applicationPlugin.getAppCode())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }
        Long count = applicationPluginMapper.selectCount(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getDelFlag, 0)
                .eq(ApplicationPlugin::getPluginType, applicationPlugin.getPluginType())
                .eq(ApplicationPlugin::getAppCode, applicationPlugin.getAppCode()));
        if (count > 0) {
            return R.fail(ResultCodeEnum.APP_IS_ADD_PLUGIN);
        }
        Application application = applicationMapper.queryAppByCode(applicationPlugin.getAppCode());
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }
        applicationPlugin.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        applicationPlugin.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        applicationPluginMapper.insert(applicationPlugin);
        redisService.sSet(Constants.REDIS_PREFIX_APP_PLUGIN + applicationPlugin.getAppCode(), applicationPlugin.getPluginType());

        // 重新发布订阅相关路由
        this.reSubscribeApp(applicationPlugin.getAppCode());

        return R.success();
    }

    /**
     * 修改服务插件
     *
     * @param applicationPlugin
     * @return
     */
    @Override
    public R<Object> updateAppPlugin(ApplicationPlugin applicationPlugin) {
        Application application = applicationMapper.queryAppByCode(applicationPlugin.getAppCode());
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        ApplicationPlugin oldPlugin = applicationPluginMapper.selectById(applicationPlugin.getId());

        applicationPlugin.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        applicationPlugin.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        applicationPluginMapper.updateById(applicationPlugin);

        if (!Objects.equals(applicationPlugin.getEnabled(), oldPlugin.getEnabled())) {
            // 停用插件
            if (applicationPlugin.getEnabled() == 0) {
                redisService.setRemove(Constants.REDIS_PREFIX_APP_PLUGIN + applicationPlugin.getAppCode(), applicationPlugin.getPluginType());
            }

            // 启用插件
            if (applicationPlugin.getEnabled() == 1) {
                redisService.sSet(Constants.REDIS_PREFIX_APP_PLUGIN + applicationPlugin.getAppCode(), applicationPlugin.getPluginType());
            }

            // 重新发布订阅相关路由
            this.reSubscribeApp(applicationPlugin.getAppCode());

        }

        return R.success();
    }

    /**
     * 获取服务插件
     *
     * @param pluginId
     * @param appCode
     * @return
     */
    @Override
    public R<Object> getAppPlugin(String pluginId, String appCode) {
        Application application = applicationMapper.queryAppByCode(appCode);
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }
        ApplicationPlugin applicationPlugin = applicationPluginMapper.selectOne(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getId, pluginId)
                .eq(ApplicationPlugin::getAppCode, appCode)
                .eq(ApplicationPlugin::getDelFlag, 0)
                .last("LIMIT 1")
        );
        List<String> type = Lists.newArrayList(PluginTypeEnum.OAUTH2.getType(), PluginTypeEnum.JWT.getType());
        if (Objects.nonNull(applicationPlugin) && type.contains(applicationPlugin.getPluginType())) {
            List<ApplicationPluginClient> applicationPluginClients = applicationPluginClientMapper.selectList(new LambdaQueryWrapper<ApplicationPluginClient>()
                    .eq(ApplicationPluginClient::getAppPluginId, applicationPlugin.getId())
                    .eq(ApplicationPluginClient::getDelFlag, 0)
            );
            if (!CollectionUtils.isEmpty(applicationPluginClients)) {
                applicationPlugin.setApplicationPluginClients(applicationPluginClients);
            }
        }
        return R.success(applicationPlugin);
    }

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    @Override
    public R<JSONObject> queryPluginConfigs(String pluginType, String appCode) {
        ApplicationPlugin appPlugin = applicationPluginMapper.selectOne(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, appCode)
                .eq(ApplicationPlugin::getPluginType, pluginType)
                .eq(ApplicationPlugin::getDelFlag, 0));
        if (null == appPlugin) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        JSONObject configJson = JSONObject.parseObject(appPlugin.getPluginParams());
        return R.success(configJson);
    }

    /**
     * 订阅时处理各服务插件及绑定关系
     *
     * @param appPlugins
     */
    @Override
    public void processPlugin(List<ApplicationPlugin> appPlugins, SysClient sysClient) {
        for (ApplicationPlugin appPlugin : appPlugins) {
            // 判断用户是否之前已订阅，生成过插件数据
            ApplicationPluginClient appPluginClient = applicationPluginClientMapper.selectOne(new LambdaQueryWrapper<ApplicationPluginClient>()
                    .eq(ApplicationPluginClient::getSysClientId, sysClient.getId())
                    .eq(ApplicationPluginClient::getAppPluginId, appPlugin.getId()));
            if (null != appPluginClient) {
                continue;
            }

            JSONObject paramJson = new JSONObject();
            // 服务发布方设置的配置项
            if (StringUtils.isNotEmpty(appPlugin.getPluginParams()) && !appPlugin.getPluginType().equals(PluginTypeEnum.SENTINEL.getType())) {
                paramJson = JSONObject.parseObject(appPlugin.getPluginParams());
            }
            String secretKey = UUID.randomUUID().toString().split("-")[0];
            ClientAppSecret secret = ClientAppSecret.builder()
                    .appCode(appPlugin.getAppCode())
                    .clientId(sysClient.getId())
                    .build();
            if ("user".equals(sysClient.getResourceType())) {
                secret.setUserId(sysClient.getResourceId());
            }
            // jwt插件
            if (appPlugin.getPluginType().equals(PluginTypeEnum.JWT.getType())) {
                paramJson.put("secretKey", secretKey);
                secret.setSecretKey(secretKey);
            }
            // oauth2插件
            else if (appPlugin.getPluginType().equals(PluginTypeEnum.OAUTH2.getType())) {
                String clientSecret = MD5Util.getMD5(secretKey);
                paramJson.put("clientId", secretKey);
                paramJson.put("clientSecret", clientSecret);
                secret.setSecretKey(clientSecret);

                JSONObject oAuthConfigJson = this.queryPluginConfigs(PluginTypeEnum.OAUTH2.getType(), appPlugin.getAppCode()).getData();
                Integer tokenExpiration = oAuthConfigJson.getInteger("TokenExpiration");
                Integer refreshTokenExpiration = oAuthConfigJson.getInteger("RefreshTokenExpiration");

                // 插入OAuth2认证客户端信息
                OauthClientDetails oldClient = oauthClientDetailsService.queryByClientId(secretKey).getData();
                if (null == oldClient) {
                    OauthClientDetails oauthClientDetails = new OauthClientDetails();
                    oauthClientDetails.setClientId(secretKey);
                    oauthClientDetails.setClientSecret(new BCryptPasswordEncoder().encode(clientSecret));
                    oauthClientDetails.setScope(Constants.OAUTH_SCOPE);
                    oauthClientDetails.setAuthorizedGrantTypes(Constants.AUTHORIZED_GRANT_TYPES);
                    oauthClientDetails.setAccessTokenValidity(tokenExpiration);
                    oauthClientDetails.setRefreshTokenValidity(refreshTokenExpiration);
                    oauthClientDetailsService.createOauthClientDetail(oauthClientDetails);
                }
            }
            // base_auth插件
            else if (appPlugin.getPluginType().equals(PluginTypeEnum.BASE_AUTH.getType())) {
                String md5 = MD5Util.getMD5(secretKey);
                paramJson.put("username", secretKey);
                paramJson.put("password", md5);
                secret.setSecretKey(md5);
            } else {
                continue;
            }

            // 保存客户端获取token的secretKey
            tokenService.saveClientAppSecretKey(secret);

            appPluginClient = new ApplicationPluginClient();
            appPluginClient.setAppPluginId(appPlugin.getId());
            appPluginClient.setSysClientId(sysClient.getId());
            appPluginClient.setPluginType(appPlugin.getPluginType());
            appPluginClient.setPluginParams(paramJson.toJSONString());
            appPluginClient.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
            appPluginClient.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
            applicationPluginClientMapper.insert(appPluginClient);
        }
    }

    /**
     * 后台重新给用户订阅服务（用于服务上架、插件变更操作）
     *
     * @param appCode
     */
    @Override
    public void reSubscribeApp(String appCode) {
        // 查询订阅该应用的所有用户，重新走订阅流程
        List<ApplicationSubscribe> appSubscribes = applicationSubscribeMapper.selectList(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppSubscribedCode, appCode));
        Set<Integer> sysClientIds = new HashSet<>();
        appSubscribes.forEach((appSubscribe) -> {
            sysClientIds.add(appSubscribe.getSubscribeClientId());
        });
        sysClientIds.forEach((clientId) -> {
            JSONObject sysUser = sysUserService.queryUserByClientId(clientId).getData();
            if (null != sysUser) {
                Integer userId = sysUser.getInteger("id");
                applicationService.appSubscribe(appCode, userId);
            }
        });
    }
}
