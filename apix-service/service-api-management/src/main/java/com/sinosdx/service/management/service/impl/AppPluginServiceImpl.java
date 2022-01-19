package com.sinosdx.service.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.consumer.OauthClientDetailsServiceFeign;
import com.sinosdx.service.management.consumer.SysUserServiceFeign;
import com.sinosdx.service.management.consumer.TokenServiceFeign;
import com.sinosdx.service.management.dao.entity.*;
import com.sinosdx.service.management.dao.mapper.*;
import com.sinosdx.service.management.enums.PluginTypeEnum;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.sentinel.SentinelProvider;
import com.sinosdx.service.management.sentinel.entity.LimitInfo;
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

    @Autowired
    private SentinelProvider service;

    @Resource
    private ApplicationPluginDetailMapper applicationPluginDetailMapper;

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

        // sentinel特殊处理
        if (PluginTypeEnum.SENTINEL.getType().equals(applicationPlugin.getPluginType())) {
            String json = applicationPlugin.getPluginParams();
            List<LimitInfo> list = JSON.parseObject(json, new TypeReference<List<LimitInfo>>() {
            });
            String save = service.save(list);
            if (!save.endsWith("ok")) {
                return R.fail();
            }
        } else {
            // 重新发布订阅相关路由
            this.reSubscribeApp(applicationPlugin.getAppCode());
        }

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

        // 启停插件
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

        // 修改插件配置
        if (!Objects.equals(applicationPlugin.getPluginParams(), oldPlugin.getPluginType())) {
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
        log.info(appCode + "重新走订阅流程");
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
                log.info(userId + "用户重新走订阅流程");
                applicationService.appSubscribe(appCode, userId);
            }
        });
    }

    /**
     * 查询已订阅应用插件详情
     *
     * @param pluginId
     * @return
     */
    @Override
    public R<Object> getSubscribedAppPluginDetails(Integer pluginId) {
        ApplicationPlugin appPlugin = applicationPluginMapper.selectById(pluginId);
        if (null == appPlugin) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }

        // 查询当前登录用户
        Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        SysClient sysClient = sysUserService.queryClientByUserId(userId).getData();
        if (null == sysClient) {
            return R.fail(ResultCodeEnum.USER_NOT_EXIST);
        }

        ApplicationPluginClient appPluginClient = applicationPluginClientMapper.selectOne(new LambdaQueryWrapper<ApplicationPluginClient>()
                .eq(ApplicationPluginClient::getAppPluginId, appPlugin.getId())
                .eq(ApplicationPluginClient::getSysClientId, sysClient.getId()));
        JSONObject pluginParamJson = new JSONObject();
        String pluginType = appPlugin.getPluginType();
        if (null != appPluginClient) {
            pluginParamJson = JSONObject.parseObject(appPluginClient.getPluginParams());
            log.info("plugin client params: " + pluginParamJson.toJSONString());
        } else {
            if (!appPlugin.getPluginType().equals(PluginTypeEnum.SENTINEL.getType())) {
                pluginParamJson = JSONObject.parseObject(appPlugin.getPluginParams());
                log.info("plugin params: " + pluginParamJson.toJSONString());
            }
        }
        // 查询详情配置
        ApplicationPluginDetail appPluginDetail = applicationPluginDetailMapper.selectOne(new LambdaQueryWrapper<ApplicationPluginDetail>()
                .eq(ApplicationPluginDetail::getPluginType, pluginType));

        switch (pluginType) {
            case "oauth2":
                appPluginDetail.setClientId(pluginParamJson.getString("clientId"));
                appPluginDetail.setClientSecret(pluginParamJson.getString("clientSecret"));
                appPluginDetail.setTokenExpiration(pluginParamJson.getString("TokenExpiration"));
                appPluginDetail.setRefreshTokenExpiration(pluginParamJson.getString("RefreshTokenExpiration"));
                appPluginDetail.setRequestUrl("http://47.103.109.225:30000/api/auth/login");
                appPluginDetail.setRequestType("POST");
                appPluginDetail.setRequestParam("{{\n" +
                        "    \"clientId\": \"" + pluginParamJson.getString("clientId") + "\",\n" +
                        "    \"clientSecret\": \"" + pluginParamJson.getString("clientSecret") + "\"\n" +
                        "}");
                appPluginDetail.setResponse("{\n" +
                        "  \"code\": 200,\n" +
                        "  \"data\": {\n" +
                        "      \"jwt\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaW5vc2R4IiwiY2xpZW50SWQiOiIxNCIsImlzcyI6IjllMmJmMzhkIiwiYXBwQ29kZSI6IjdiNDA4MTk2IiwiZXhwIjoxNjQyMTM3MTIwLCJ1c2VySWQiOiI4In0.SBTVbVE13g2s1-MrlLp3aDih3FmRKObWcwXdK4-kIeg\"\n" +
                        "  },\n" +
                        "  \"msg\": \"提交成功\",\n" +
                        "  \"path\": \"\",\n" +
                        "  \"success\": true,\n" +
                        "  \"timestamp\": 1642129920702\n" +
                        "}");
                break;
            case "jwt":
                appPluginDetail.setClaimValue(pluginParamJson.getString("secretKey"));
                appPluginDetail.setHeader(pluginParamJson.getString("HeaderNames"));
                appPluginDetail.setClaimKey(pluginParamJson.getString("keyClaimName"));
                appPluginDetail.setTokenExpiration(pluginParamJson.getString("TokenExpiration"));
                appPluginDetail.setRequestUrl("http://47.103.109.225:30000/api/auth/token/jwt");
                appPluginDetail.setRequestType("POST");
                appPluginDetail.setRequestParam("{\n" +
                        "    \"claimValue\": \"" + pluginParamJson.getString("secretKey") + "\"\n" +
                        "}");
                appPluginDetail.setResponse("{\n" +
                        "  \"code\": 200,\n" +
                        "  \"data\": {\n" +
                        "      \"jwt\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaW5vc2R4IiwiY2xpZW50SWQiOiIxNCIsImlzcyI6IjllMmJmMzhkIiwiYXBwQ29kZSI6IjdiNDA4MTk2IiwiZXhwIjoxNjQyMTM3MTIwLCJ1c2VySWQiOiI4In0.SBTVbVE13g2s1-MrlLp3aDih3FmRKObWcwXdK4-kIeg\"\n" +
                        "  },\n" +
                        "  \"msg\": \"提交成功\",\n" +
                        "  \"path\": \"\",\n" +
                        "  \"success\": true,\n" +
                        "  \"timestamp\": 1642129920702\n" +
                        "}");
                break;
            case "base_auth":
                appPluginDetail.setUsername(pluginParamJson.getString("username"));
                appPluginDetail.setPassword(pluginParamJson.getString("password"));
                appPluginDetail.setRequestUrl("http://47.103.109.225:30000/api/auth/token/basic");
                appPluginDetail.setRequestType("POST");
                appPluginDetail.setRequestParam("{\n" +
                        "    \"username\": \"" + pluginParamJson.getString("username") + "\",\n" +
                        "    \"password\": \"" + pluginParamJson.getString("password") + "\"\n" +
                        "}");
                appPluginDetail.setResponse("{\n" +
                        "  \"code\": 200,\n" +
                        "  \"data\": {\n" +
                        "      \"token\": \"YzYxNzY2YWE6MzkwMjllYTIyMWYyNGI3NDI1NWM1NGQ2N2RmNDNZDY=\"\n" +
                        "  },\n" +
                        "  \"msg\": \"提交成功\",\n" +
                        "  \"path\": \"\",\n" +
                        "  \"success\": true,\n" +
                        "  \"timestamp\": 1642130058357\n" +
                        "}");
                break;
            case "cors":
                appPluginDetail.setAllowOrigins(pluginParamJson.getString("allowOrigins"));
                appPluginDetail.setAllowHeaders(pluginParamJson.getString("allowHeaders"));
                appPluginDetail.setExposeHeaders(pluginParamJson.getString("exposeHeaders"));
                appPluginDetail.setAllowMethods(pluginParamJson.getString("allowMethods"));
                appPluginDetail.setAllowCredentials(pluginParamJson.getString("allowCredentials"));
                appPluginDetail.setMaxAge(pluginParamJson.getString("maxAge"));
                break;
            case "black_list_ip":
                appPluginDetail.setBlackListIp(pluginParamJson.getString("black_list_ip"));
                break;
            case "white_list_ip":
                appPluginDetail.setWhiteListIp(pluginParamJson.getString("white_list_ip"));
                break;
            case "sign":
                appPluginDetail.setSecretKey(pluginParamJson.getString("appKey") + pluginParamJson.getString("appSecret"));
                break;
            case "sentinel":
                JSONArray jsonArray = JSONObject.parseArray(appPlugin.getPluginParams());
                List<JSONObject> apiConfigList = new ArrayList<>();
                jsonArray.forEach((jsonObj) -> {
                    JSONObject jsonObject = JSONObject.parseObject(jsonObj.toString());
                    String path = jsonObject.getString("path");
                    // 控流时长单位
                    String unit = null;
                    switch (jsonObject.getString("intervalUnit")) {
                        case "0":
                            unit = "秒";
                            break;
                        case "1":
                            unit = "分钟";
                            break;
                        case "2":
                            unit = "小时";
                            break;
                        default:
                    }
                    if (StringUtils.isBlank(path)) {
                        appPluginDetail.setSentinelCount(jsonObject.getInteger("count"));
                        appPluginDetail.setSentinelInterval(jsonObject.getInteger("interval") + unit);
                    } else {
                        JSONObject apiConfigJson = new JSONObject();
                        apiConfigJson.put("apiName", jsonObject.getJSONObject("apiId").getString("apiName"));
                        apiConfigJson.put("apiInterval", jsonObject.getInteger("interval") + unit);
                        apiConfigJson.put("apiCount", jsonObject.getInteger("count"));
                        apiConfigList.add(apiConfigJson);
                    }
                });
                appPluginDetail.setSentinelApiConfig(apiConfigList);
                break;
            default:
        }

        return R.success(appPluginDetail);
    }
}
