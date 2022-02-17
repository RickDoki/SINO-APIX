package com.sinosdx.service.management.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.sinosdx.common.base.base.entity.Entity;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.consumer.OauthClientDetailsServiceFeign;
import com.sinosdx.service.management.consumer.SysUserServiceFeign;
import com.sinosdx.service.management.consumer.TokenServiceFeign;
import com.sinosdx.service.management.controller.dto.ApplicationNumDTO;
import com.sinosdx.service.management.controller.dto.ApplicationSubscribeDto;
import com.sinosdx.service.management.controller.dto.ApplicationVersionDto;
import com.sinosdx.service.management.controller.vo.*;
import com.sinosdx.service.management.dao.entity.*;
import com.sinosdx.service.management.dao.mapper.*;
import com.sinosdx.service.management.enums.PluginTypeEnum;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.sentinel.SentinelProvider;
import com.sinosdx.service.management.service.ApiService;
import com.sinosdx.service.management.service.AppApiGatewayService;
import com.sinosdx.service.management.service.AppPluginService;
import com.sinosdx.service.management.service.ApplicationService;
import com.sinosdx.service.management.utils.MD5Util;
import com.sinosdx.service.management.utils.ThreadContext;
import com.sinosdx.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Slf4j
@Service
@RefreshScope
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private ApplicationDeveloperMapper applicationDeveloperMapper;

    @Resource
    private ApplicationVersionMapper applicationVersionMapper;

    @Resource
    private ApplicationApiMapper applicationApiMapper;

    @Resource
    private ApplicationLeaseMapper applicationLeaseMapper;

    @Resource
    private ApiMapper apiMapper;

    @Autowired
    private OauthClientDetailsServiceFeign oauthClientDetailsService;

    @Autowired
    private SysUserServiceFeign sysUserService;

    @Autowired
    private RedisService redisService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ApplicationPluginMapper applicationPluginMapper;

    @Resource
    private ApplicationSubscribeMapper applicationSubscribeMapper;

    @Resource
    private ApplicationPluginClientMapper applicationPluginClientMapper;

    @Autowired
    private SentinelProvider sentinelProvider;

    @Autowired
    private AppPluginService appPluginService;

    @Autowired
    private AppApiGatewayService appApiGatewayService;

    @Value("${domain.gateway:https://apix.sinosdx.cn/api}")
    private String gatewayDomain;

    @Autowired
    private ApiService apiService;

    @Autowired
    private TokenServiceFeign tokenService;

    @Autowired
    private ExecutorService executorService;

    /**
     * 创建新应用
     *
     * @param application
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> createApplication(Application application) {
        //        if (StringUtils.isBlank(application.getCode())) {
        //            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        //        }

        if (StringUtils.isBlank(application.getName())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        //        // code中不允许包含"-"
        //        if (application.getCode().contains("-")) {
        //            return R.fail(ResultCodeEnum.APP_CODE_ERROR);
        //        }
        //
        //        // code中不允许包含中文
        //        if (Pattern.matches(Constants.REGEX_CHINESE, application.getCode())) {
        //            return R.fail(ResultCodeEnum.APP_CODE_ERROR_CHINESE);
        //        }

        // 插入application
        application.setCode(UUID.randomUUID().toString().split("-")[0]);
        application.setIsPublished(Constants.APP_STATUS_IS_NOT_PUBLISHED);
        application.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        application.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        application.setCreationByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        application.setProvider(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        application.setPublishDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        Long existApp = applicationMapper.selectCount(new QueryWrapper<Application>()
                .eq("name", application.getName())
                .or().eq("code", application.getCode())
                .eq("del_flag", 0));
        if (existApp > 0) {
            return R.fail(ResultCodeEnum.APPLICATION_IS_EXIST);
        }
        applicationMapper.insert(application);

        return R.success(application);
    }

    /**
     * 查询应用列表
     *
     * @param developerId 开发者id（传入此参数显示开发者关联的应用列表）
     * @param appName
     * @param appCode
     * @param appId
     * @param isPublished 是否发布到资源市场（传入此参数显示资源市场应用列表）
     * @param startTime   开始时间戳查询（根据创建时间）
     * @param endTime     结束时间戳查询（根据创建时间）
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryApplicationList(Integer developerId, String appName, String appCode, Integer appId,
                                          String isPublished, Long startTime, Long endTime, Integer limit, Integer offset, Boolean market) {
        if (null == startTime) {
            startTime = DateUtils.addYears(new Date(), -1).getTime();
        }
        if (null == endTime) {
            endTime = System.currentTimeMillis();
        }

        List<String> statusList = new ArrayList<>();
        List<Integer> userIdList = new ArrayList<>();
        if (null != isPublished || null == market || !market) {
            statusList = null;
            userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        } else {
            //            statusList.add(Constants.APP_STATUS_IS_ADDED);
            //            statusList.add(Constants.APP_STATUS_ERROR);
            statusList.add(Constants.APP_STATUS_IS_PUBLISHED);
        }
        List<Map<String, Object>> applicationVos = applicationMapper.queryAppVoList(developerId, appName, appCode,
                appId, isPublished, LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8)),
                LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8)),
                limit, offset == null || limit == null ? null : limit * (offset - 1), statusList, userIdList);
        Integer clientId = null;
        if (null != market && market) {
            Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
            if (Objects.nonNull(userId)) {
                SysClient sysClient = sysUserService.queryClientByUserId(userId).getData();
                clientId = sysClient.getId();
            }

        }
        // 将标签处理为数组返回给前端
        Integer finalClientId = clientId;
        applicationVos.forEach(map -> {
            Object label = map.get("label");
            List<String> labelList = new ArrayList<>();
            if (null != label) {
                labelList = Arrays.stream(String.valueOf(label).split("/")).filter(Objects::nonNull).collect(Collectors.toList());
            }
            map.put("label", labelList);
            // 查询版本
            List<String> appVersions = applicationVersionMapper.selectList(new LambdaQueryWrapper<ApplicationVersion>()
                            .eq(ApplicationVersion::getAppId, map.get("appId"))
                            .eq(ApplicationVersion::getDelFlag, 0))
                    .stream().map(ApplicationVersion::getVersion).collect(Collectors.toList());
            map.put("appVersions", appVersions);
            map.put("subscribed", false);
            if (market && Objects.nonNull(finalClientId)) {
                Long count = applicationSubscribeMapper.selectCount(new LambdaQueryWrapper<ApplicationSubscribe>()
                        .eq(ApplicationSubscribe::getAppSubscribedCode, map.get("appCode"))
                        .eq(ApplicationSubscribe::getSubscribeClientId, finalClientId)
                        .eq(ApplicationSubscribe::getDelFlag, 0)
                );
                if (count > 0) {
                    map.put("subscribed", true);
                }
            }
            // 加入插件信息
            List<ApplicationPlugin> applicationPlugins = applicationPluginMapper
                    .selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                            .eq(ApplicationPlugin::getAppCode, map.get("appCode"))
                            .orderByDesc(ApplicationPlugin::getCreationDate)
                            .eq(ApplicationPlugin::getDelFlag, 0));
            map.put("plugins", applicationPlugins);
        });
        Map<String, Object> appListMap = new HashMap<>();
        List<Map<String, Object>> applicationVoList = applicationMapper.queryAppVoList(developerId, appName, appCode,
                appId, isPublished, LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8)),
                LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8)), null, null, statusList, userIdList);
        appListMap.put("appList", applicationVos);
        appListMap.put("total", applicationVoList.size());
        return R.success(appListMap);
    }

    /**
     * 查看应用详情
     *
     * @param appCode
     * @param developerId
     * @return
     */
    @Override
    public R<Object> queryApplicationDetails(String appCode, Integer developerId) {
        Map<String, Object> appDetailMap = applicationMapper.queryAppDetails(appCode);
        if (null == appDetailMap) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        // 查询创建者姓名
        Map<String, Object> appCreationBy = sysUserService.findUserById((Integer) appDetailMap.get("appCreationBy")).getData();
        //        JSONObject appCreationBy = ompService.queryOmpUser(null, (Integer) appDetailMap.get("appCreationBy"), null).getData();
        if (null != appCreationBy) {
            appDetailMap.put("appCreationUser", appCreationBy.get("username"));
        } else {
            appDetailMap.put("appCreationUser", "-");
        }

        // 查询修改者姓名
        Map<String, Object> appLastUpdate;
        //        JSONObject appLastUpdate;
        if (appDetailMap.get("appCreationBy").equals(appDetailMap.get("appLastUpdateBy"))) {
            appLastUpdate = appCreationBy;
        } else {
            appLastUpdate = sysUserService.findUserById((Integer) appDetailMap.get("appLastUpdateBy")).getData();
            //            appLastUpdate = ompService.queryOmpUser(null, (Integer) appDetailMap.get("appLastUpdateBy"), null).getData();
        }
        if (null != appLastUpdate) {
            appDetailMap.put("appLastUpdateUser", appLastUpdate.get("username"));
        } else {
            appDetailMap.put("appLastUpdateUser", "-");
        }

        //        if (null != developerId) {
        //            List<Map<String, Object>> usingAppList = applicationMapper.queryUsingAppList(appCode);
        //            appDetailMap.put("usingAppList", usingAppList);
        //        }
        appDetailMap.put("subscribed", false);
        Integer clientId = null;
        Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        if (null != userId) {
            clientId = (sysUserService.queryClientByUserId(userId).getData()).getId();
            Long count = applicationSubscribeMapper.selectCount(new LambdaQueryWrapper<ApplicationSubscribe>()
                    .eq(ApplicationSubscribe::getAppSubscribedCode, appCode)
                    .eq(ApplicationSubscribe::getSubscribeClientId, clientId)
                    .eq(ApplicationSubscribe::getDelFlag, 0)
            );
            if (count > 0) {
                appDetailMap.put("subscribed", true);
            }
        }
        if (null != developerId) {
            LambdaQueryWrapper<ApplicationSubscribe> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Objects.nonNull(clientId), ApplicationSubscribe::getSubscribeClientId, clientId)
                    .eq(Objects.nonNull(appCode), ApplicationSubscribe::getAppSubscribedCode, appCode)
                    .eq(ApplicationSubscribe::getDelFlag, 0);
            List<ApplicationSubscribe> applicationSubscribes = applicationSubscribeMapper.selectList(wrapper);
            appDetailMap.put("usingAppList", applicationSubscribes);
        }


        // 加入插件信息
        List<ApplicationPlugin> applicationPlugins = applicationPluginMapper
                .selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                        .eq(ApplicationPlugin::getAppCode, appCode)
                        .orderByDesc(ApplicationPlugin::getCreationDate)
                        .eq(ApplicationPlugin::getDelFlag, 0));
        appDetailMap.put("plugins", applicationPlugins);

        return R.success(appDetailMap);
    }

    /**
     * 修改应用
     *
     * @param applicationVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> updateApplication(ApplicationVo applicationVo) {
        String msg = "更新成功";
        Application oldApp = applicationMapper.selectOne(
                new QueryWrapper<Application>().eq("code", applicationVo.getAppCode()).eq("del_flag", 0));
        if (null == oldApp) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }
        if (StringUtils.isNotEmpty(applicationVo.getAppName())) {
            if (!applicationVo.getAppName().equals(oldApp.getName())) {
                Long count = applicationMapper.selectCount(
                        new QueryWrapper<Application>().eq("name", applicationVo.getAppName()).eq("del_flag", 0));
                if (count > 0) {
                    return R.fail(ResultCodeEnum.APPLICATION_IS_EXIST);
                }
            }
            oldApp.setName(applicationVo.getAppName());
        }
        if (StringUtils.isNotEmpty(applicationVo.getDescription())) {
            oldApp.setDescription(applicationVo.getDescription());
        }
        if (StringUtils.isNotEmpty(applicationVo.getMarkdown())) {
            oldApp.setMarkdown(applicationVo.getMarkdown());
        }
        if (StringUtils.isNotEmpty(applicationVo.getIconUrl())) {
            oldApp.setIconUrl(applicationVo.getIconUrl());
        }
        if (StringUtils.isNotEmpty(applicationVo.getIsPublished())) {
            switch (applicationVo.getIsPublished()) {
                case Constants.APP_STATUS_IS_NOT_PUBLISHED:
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_PUBLISHED)) {
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                        msg = "下架成功";

                        // 删除应用相关api路由
                        List<Api> apiList = apiMapper.queryApiListByCondition(applicationVo.getAppCode(), null);
//                        List<Api> orphanApiList = apiService.getApiListNotUsedByOtherAppOrAppVersion(apiList).getData();
                        executorService.execute(() -> appApiGatewayService.deleteApiGatewayConfig(applicationVo.getAppId(), apiList, null));
//                        appApiGatewayService.deleteApiGatewayConfig(applicationVo.getAppId(), apiList, null);
                    } else {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    }
                    break;
                case Constants.APP_STATUS_IS_PUBLISHED:
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_NOT_PUBLISHED)) {
                        Long count = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                                .eq("app_code", oldApp.getCode()).eq("del_flag", 0));
                        if (count < 1) {
                            return R.fail(ResultCodeEnum.APP_VERSION_IS_NOT_EXIST);
                        }
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                        oldApp.setPublishDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
                        msg = "上架成功";

                        // 重新发布订阅相关路由
                        appPluginService.reSubscribeApp(applicationVo.getAppCode());
                    } else {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    }
                    break;
                default:
            }
        }
        oldApp.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        oldApp.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        oldApp.setLastUpdatedByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        applicationMapper.updateById(oldApp);

//        // 如果停用应用，需要注销对应客户端token
//        if (null != applicationVo.getIsPublished() && Constants.APP_STATUS_IS_NOT_PUBLISHED.equals(applicationVo.getIsPublished())) {
//            revokeClientToken(oldApp.getCode());
//        }

        return R.successDef(oldApp, msg);
    }

    /**
     * 删除应用
     *
     * @param appCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> deleteApplication(String appCode) {
        // 判断应用是否绑定了其他服务
        //        Long count = applicationLeaseMapper.selectCount(new QueryWrapper<ApplicationLease>()
        //                .eq("app_lessee_code", appCode).or().eq("app_lessor_code", appCode).eq("del_flag", 0));
        //        if (count > 0) {
        //            return R.fail(ResultCodeEnum.APP_BE_USED_OR_USING_OTHER_APP);
        //        }
        Application application = applicationMapper.queryAppByCode(appCode);
        Integer appId = application.getId();
        // 删除应用
        applicationMapper.delete(new QueryWrapper<Application>().eq("code", appCode));
        // 删除应用版本
        applicationVersionMapper.delete(new QueryWrapper<ApplicationVersion>().eq("app_code", appCode));
        // 删除api路由
        List<Api> apiList = apiMapper.queryApiListByCondition(appCode, null);
//        List<Api> orphanApiList = apiService.getApiListNotUsedByOtherAppOrAppVersion(apiList).getData();
        executorService.execute(() ->
                appApiGatewayService.deleteApiGatewayConfig(appId, apiList, null));
        // 删除版本api关联
        applicationApiMapper.delete(new QueryWrapper<ApplicationApi>().eq("app_code", appCode));
        // 刪除订阅关系
        applicationSubscribeMapper.delete(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppSubscribedCode, appCode)
                .eq(ApplicationSubscribe::getDelFlag, 0));
        // 删除关联的插件
        LambdaQueryWrapper<ApplicationPlugin> wrapper = new LambdaQueryWrapper<ApplicationPlugin>().eq(ApplicationPlugin::getAppCode, appCode);
        List<Integer> idList = applicationPluginMapper.selectList(wrapper).stream().map(Entity::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idList)) {
            applicationPluginMapper.deleteBatchIds(idList);
            // 删除 用户和插件关联表
            applicationPluginClientMapper.delete(new LambdaQueryWrapper<ApplicationPluginClient>().in(ApplicationPluginClient::getAppPluginId, idList));
        }
        // 删除对应客户端认证信息
        ClientAppSecret secret = tokenService.querySecretByAppCode(appCode).getData();
        if (null != secret) {
            oauthClientDetailsService.deleteOAuthClientDetail(secret.getSecretKey());
            redisService.del("client_id_to_access:" + secret.getSecretKey());
        }
        tokenService.deleteClientAppSecret(appCode);

        return R.success();
    }

    /**
     * 发布应用新版本
     *
     * @param applicationVersionVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> publishNewAppVersion(ApplicationVersionVo applicationVersionVo) {
        Application application = applicationMapper.selectOne(
                new QueryWrapper<Application>().eq("code", applicationVersionVo.getAppCode()).eq("del_flag", 0));
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        if (StringUtils.isBlank(applicationVersionVo.getAppVersion())) {
            return R.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }

        Long count = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                .eq("app_code", applicationVersionVo.getAppCode())
                .eq("version", applicationVersionVo.getAppVersion())
                .eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.APP_VERSION_IS_EXIST);
        }

        String[] apiIdList = applicationVersionVo.getApiIds().split(",");
        List<Api> apiList = new ArrayList<>();
        for (String apiIdStr : apiIdList) {
            Integer apiId = Integer.valueOf(apiIdStr);
            Api api = apiMapper.selectById(apiId);
            if (null == api) {
                return R.fail(ResultCodeEnum.API_IS_NOT_EXIST);
            }
            apiList.add(api);
        }

        // 验证路由是否发布过
        //        for (Api api : apiList) {
        //            String urlCode = StringUtils.isEmpty(application.getProductId()) ? application.getCode() : application.getProductId();
        //            ApplicationApiGateway applicationApiGateway = appApiGatewayMapper.selectOne(new QueryWrapper<ApplicationApiGateway>()
        //                    .eq("url_code", urlCode)
        //                    .eq("domain", api.getDomain())
        //                    .eq("api_url", api.getUrl()));
        //            if (null != applicationApiGateway) {
        //                return R.fail("/" + urlCode + api.getUrl() + "已被发布到网关，请更换api发布");
        //            }
        //        }

        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setVersion(applicationVersionVo.getAppVersion());
        applicationVersion.setAppId(application.getId());
        applicationVersion.setAppCode(applicationVersionVo.getAppCode());
        applicationVersion.setDescription(applicationVersionVo.getVersionDesc());
        applicationVersion.setMarkdown(applicationVersionVo.getMarkdown());
        applicationVersionMapper.insert(applicationVersion);

        List<ApiVo> apiVoList = new ArrayList<>();
        for (Api api : apiList) {
            //            ApplicationApi applicationApi = applicationApiMapper.selectOne(new LambdaQueryWrapper<ApplicationApi>()
            //                    .eq(ApplicationApi::getAppId, application.getId())
            //                    .eq(ApplicationApi::getApiId, api.getId())
            //                    .eq(ApplicationApi::getDelFlag, 0));
            //            if (null == applicationApi) {
            ApplicationApi applicationApi = new ApplicationApi();
            applicationApi.setAppId(application.getId());
            applicationApi.setAppVersionId(applicationVersion.getId());
            applicationApi.setAppCode(application.getCode());
            applicationApi.setApiId(api.getId());
            applicationApiMapper.insert(applicationApi);
            //            }

            apiVoList.add(new ApiVo(api));
        }

        // 查询服务插件
        List<ApplicationPlugin> plugins = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, application.getCode())
                .eq(ApplicationPlugin::getEnabled, 1));

        //单独查询是否包含sentinel 插件
        long sentinel = plugins.stream().filter(a -> PluginTypeEnum.SENTINEL.getType().equals(a.getPluginType())).count();
        if (sentinel > 0) {
            sentinelProvider.addOrRefreshApiGroup(application.getId() + "");
        }

        // 重新发布订阅相关路由
        appPluginService.reSubscribeApp(applicationVersionVo.getAppCode());

        return R.success(new ApplicationVersionVo(applicationVersion, apiVoList));
    }

    /**
     * 绑定应用服务（使用资源市场应用服务）
     *
     * @param appLesseeCode
     * @param appLessorCode
     * @return
     */
    @Deprecated
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> appLease(String appLesseeCode, String appLessorCode) {
        List<Application> lesseeAppList = applicationMapper.queryAppForLease(appLesseeCode,
                Arrays.asList(Constants.APP_STATUS_IS_PUBLISHED, Constants.APP_STATUS_ERROR, Constants.APP_STATUS_IS_ADDED),
                null);
        if (lesseeAppList.isEmpty()) {
            return R.fail(ResultCodeEnum.LESSEE_APP_IS_NOT_EXIST);
        }
        List<Application> lessorAppList = applicationMapper.queryAppForLease(appLessorCode,
                Arrays.asList(Constants.APP_STATUS_ERROR, Constants.APP_STATUS_IS_ADDED),
                null);
        if (lessorAppList.isEmpty()) {
            return R.fail(ResultCodeEnum.LESSOR_APP_IS_NOT_EXIST);
        }

        Long appVersionCount = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                .eq("app_code", appLessorCode).eq("del_flag", 0));
        if (appVersionCount == 0) {
            return R.fail(ResultCodeEnum.NONE_APP_VERSION);
        }

        Long count = applicationLeaseMapper.selectCount(new QueryWrapper<ApplicationLease>()
                .eq("app_lessee_code", appLesseeCode)
                .eq("app_lessor_code", appLessorCode)
                .eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.APP_LEASE_IS_EXIST);
        }

        Application lesseeApp = lesseeAppList.get(0);
        Application lessorApp = lessorAppList.get(0);

        String clientSecret = MD5Util.getMD5(appLessorCode);

        // 插入OAuth2认证客户端信息
        String clientId = appLesseeCode + "-" + appLessorCode;
        log.info("clientId: " + clientId);
        OauthClientDetails oldClient = oauthClientDetailsService.queryByClientId(clientId).getData();
        if (null == oldClient) {
            OauthClientDetails oauthClientDetails = new OauthClientDetails();
            oauthClientDetails.setClientId(clientId);
            oauthClientDetails.setClientSecret(new BCryptPasswordEncoder().encode(clientSecret));
            oauthClientDetails.setScope(Constants.OAUTH_SCOPE);
            oauthClientDetails.setAuthorizedGrantTypes(Constants.AUTHORIZED_GRANT_TYPES);
            oauthClientDetails.setAccessTokenValidity(Constants.ACCESS_TOKEN_VALIDITY);
            oauthClientDetails.setRefreshTokenValidity(Constants.REFRESH_TOKEN_VALIDITY);
            Map<String, String> map = new HashMap<>();
            map.put("lessorCode", appLessorCode);
            map.put("lesseeCode", appLesseeCode);
            oauthClientDetails.setAdditionalInformation(JSONObject.toJSONString(map));
            log.info(oauthClientDetails.toString());
            oauthClientDetailsService.createOauthClientDetail(oauthClientDetails);
        }

        ApplicationLease applicationLease = new ApplicationLease();
        applicationLease.setAppLesseeId(lesseeApp.getId());
        applicationLease.setAppLesseeCode(appLesseeCode);
        applicationLease.setAppLesseeName(lesseeApp.getName());
        applicationLease.setAppLessorId(lessorApp.getId());
        applicationLease.setAppLessorCode(appLessorCode);
        applicationLease.setAppLessorName(lessorApp.getName());
        applicationLease.setClientId(clientId);
        applicationLease.setClientSecret(clientSecret);
        applicationLease.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        applicationLease.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        applicationLease.setCreationByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        applicationLeaseMapper.insert(applicationLease);

        return R.success(new ApplicationLeaseVo(applicationLease));
    }

    /**
     * 订阅服务
     *
     * @param appSubscribedCode
     * @param sysUserId
     * @return
     */
    @Override
    public R<Object> appSubscribe(String appSubscribedCode, Integer sysUserId) {
        Application subscribedApp = applicationMapper.queryAppByStatus(appSubscribedCode,
                Arrays.asList(Constants.APP_STATUS_IS_PUBLISHED, Constants.APP_STATUS_ERROR, Constants.APP_STATUS_IS_ADDED));
        if (null == subscribedApp) {
            return R.fail(ResultCodeEnum.LESSEE_APP_IS_NOT_EXIST);
        }

        Long appVersionCount = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                .eq("app_code", appSubscribedCode).eq("del_flag", 0));
        if (appVersionCount == 0) {
            return R.fail(ResultCodeEnum.NONE_APP_VERSION);
        }

        SysClient sysClient = sysUserService.queryClientByUserId(sysUserId).getData();
        if (null == sysClient) {
            return R.fail(ResultCodeEnum.RESOURCE_NOT_EXISTED);
        }

        ApplicationSubscribe applicationSubscribe = applicationSubscribeMapper.selectOne(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppSubscribedCode, appSubscribedCode)
                .eq(ApplicationSubscribe::getAppSubscribedId, subscribedApp.getId())
                .eq(ApplicationSubscribe::getSubscribeClientId, sysClient.getId())
                .eq(ApplicationSubscribe::getDelFlag, 0));

        if (null == applicationSubscribe) {
            String appClientCode = UUID.randomUUID().toString().split("-")[0];
            applicationSubscribe = new ApplicationSubscribe();
            applicationSubscribe.setAppSubscribedId(subscribedApp.getId());
            applicationSubscribe.setAppSubscribedCode(subscribedApp.getCode());
            applicationSubscribe.setSubscribeClientId(sysClient.getId());
            applicationSubscribe.setAppClientCode(appClientCode);
            applicationSubscribe.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
            applicationSubscribe.setCreationBy(sysUserId);
            applicationSubscribeMapper.insert(applicationSubscribe);
        }

        // 查询服务插件
        List<ApplicationPlugin> plugins = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, subscribedApp.getCode())
                .eq(ApplicationPlugin::getEnabled, 1));
        log.info(subscribedApp.getCode()+" plugins size : "+plugins.size());

        //单独查询是否包含sentinel 插件
        long sentinel = plugins.stream().filter(a -> PluginTypeEnum.SENTINEL.getType().equals(a.getPluginType())).count();
        if (sentinel > 0) {
            sentinelProvider.addOrRefreshApiGroup(subscribedApp.getId() + "");
        }

        // 生成订阅用户调用信息
        appPluginService.processPlugin(plugins, sysClient);

        // 查询服务所有关联api
        List<Api> apiList = apiMapper.queryApiListByCondition(subscribedApp.getCode(), null);

        // 异步发布到网关
        String appClientCode = applicationSubscribe.getAppClientCode();
        executorService.execute(() ->
                appApiGatewayService.updateApiGatewayConfig(subscribedApp.getId(), apiList, appClientCode));
//        appApiGatewayService.updateApiGatewayConfig(subscribedApp.getId(), apiList, appClientCode);

        return R.success(applicationSubscribe);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public R<Object> unSubscribe(String appSubscribedCode) {
        Application subscribedApp = applicationMapper.queryAppByStatus(appSubscribedCode,
                Arrays.asList(Constants.APP_STATUS_IS_PUBLISHED, Constants.APP_STATUS_ERROR, Constants.APP_STATUS_IS_ADDED));
        if (null == subscribedApp) {
            return R.fail(ResultCodeEnum.LESSEE_APP_IS_NOT_EXIST);
        }

        Long appVersionCount = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                .eq("app_code", appSubscribedCode).eq("del_flag", 0));
        if (appVersionCount == 0) {
            return R.fail(ResultCodeEnum.NONE_APP_VERSION);
        }

        SysClient sysClient = sysUserService.queryClientByUserId(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID)).getData();
        if (null == sysClient) {
            return R.fail(ResultCodeEnum.RESOURCE_NOT_EXISTED);
        }
        // 进行解绑
        // 1.删除plugin相关数据
        applicationPluginClientMapper.delete(new LambdaQueryWrapper<ApplicationPluginClient>()
                .eq(ApplicationPluginClient::getSysClientId, sysClient.getId())
                .eq(ApplicationPluginClient::getDelFlag, 0)
        );
        // 查询服务插件
        List<ApplicationPlugin> plugins = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, subscribedApp.getCode())
                .eq(ApplicationPlugin::getEnabled, 1));

        //单独查询是否包含sentinel 插件
        long sentinel = plugins.stream().filter(a -> PluginTypeEnum.SENTINEL.getType().equals(a.getPluginType())).count();
        if (sentinel > 0) {
            sentinelProvider.addOrRefreshApiGroup(subscribedApp.getId() + "");
        }

        // 2.删除 applicationSubscribe
        List<ApplicationSubscribe> appSubscribes = applicationSubscribeMapper.selectList(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppSubscribedCode, appSubscribedCode)
                .eq(ApplicationSubscribe::getAppSubscribedId, subscribedApp.getId())
                .eq(ApplicationSubscribe::getSubscribeClientId, sysClient.getId())
                .eq(ApplicationSubscribe::getDelFlag, 0));
        if (CollectionUtils.isEmpty(appSubscribes)) {
            log.error("订阅数据有误");
            return R.success();
        }
        String appClientCode = appSubscribes.get(0).getAppClientCode();
        applicationSubscribeMapper.delete(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppSubscribedCode, appSubscribedCode)
                .eq(ApplicationSubscribe::getAppSubscribedId, subscribedApp.getId())
                .eq(ApplicationSubscribe::getSubscribeClientId, sysClient.getId())
                .eq(ApplicationSubscribe::getDelFlag, 0));

        // 3.删除对应路由
        List<Api> apiList = apiMapper.queryApiListByCondition(subscribedApp.getCode(), null);
//        List<Api> orphanApiList = apiService.getApiListNotUsedByOtherAppOrAppVersion(apiList).getData();
        executorService.execute(() ->
                appApiGatewayService.deleteApiGatewayConfig(subscribedApp.getId(), apiList, appClientCode));
//        appApiGatewayService.deleteApiGatewayConfig(subscribedApp.getId(), apiList, appClientCode);

        return R.success();
    }


    /**
     * 添加应用开发者
     *
     * @param appCode
     * @param phone
     * @return
     */
    @Override
    public R<Object> addAppDeveloper(String appCode, String phone) {
        Application application = applicationMapper.selectOne(
                new QueryWrapper<Application>().eq("code", appCode).eq("del_flag", 0));
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        Long count = applicationDeveloperMapper.selectCount(new QueryWrapper<ApplicationDeveloper>()
                .eq("app_code", appCode)
                .eq("phone", phone).eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.APP_DEVELOPER_IS_ADDED);
        }

        Map<String, Object> user = (Map<String, Object>) sysUserService.selectUserByPhoneAndUsername(null, phone).getData();
        //        JSONObject user = ompService.queryOmpUser(phone, null, username).getData();
        if (null == user) {
            return R.fail(ResultCodeEnum.APP_DEVELOPER_IS_NOT_EXIST);
        }

        ApplicationDeveloper applicationDeveloper = new ApplicationDeveloper();
        applicationDeveloper.setAppId(application.getId());
        applicationDeveloper.setAppCode(appCode);
        applicationDeveloper.setUserId(Integer.parseInt(user.get("id").toString()));
        applicationDeveloper.setUsername(user.get("username").toString());
        applicationDeveloper.setPhone(user.get("mobile").toString());
        applicationDeveloper.setIsCreator(false);
        applicationDeveloperMapper.insert(applicationDeveloper);
        return R.success();
    }

    /**
     * 查看应用开发者列表
     *
     * @param appCode
     * @return
     */
    @Override
    public R<Object> queryAppDeveloperList(String appCode) {
        Application application = applicationMapper.selectOne(
                new QueryWrapper<Application>().eq("code", appCode).eq("del_flag", 0));
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        List<Map<String, Object>> maps = applicationDeveloperMapper.queryAppDeveloperList(appCode);
        return R.success(maps);
    }

    /**
     * 移除应用开发者
     *
     * @param appCode
     * @param developerId
     * @return
     */
    @Override
    public R<Object> deleteAppDeveloper(String appCode, Integer developerId) {
        Application application = applicationMapper.queryAppByCode(appCode);
        if (null == application) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        if (!application.getCreationBy().equals(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID))) {
            return R.fail(ResultCodeEnum.NOT_APP_CREATOR);
        }

        if (application.getCreationBy().equals(developerId)) {
            return R.fail(ResultCodeEnum.CAN_NOT_DELETE_APP_CREATOR);
        }
        applicationDeveloperMapper.delete(new QueryWrapper<ApplicationDeveloper>()
                .eq("app_code", appCode).eq("user_id", developerId).eq("del_flag", 0));
        return R.success();
    }

    /**
     * 提供首页仪表盘所需第一行和第五行数据
     * 首页仪表盘：
     * <p>
     * 一、第一行
     * 1、开发者应用总数
     * 形式：数值
     * 说明：当前平台内所有应用的数量，包含创建未发布、上架和未上架的
     * 2、开发者API总数
     * 形式：数值
     * 说明：所有状态正常的API总数
     * 3、上架开放应用总数
     * 形式：数值
     * 说明：上架的应用数量，不区分应用版本
     * 4、开发者总数
     * 形式：数值
     * 说明：权限包含开发者权限的用户总数
     * <p>
     * 五、第五行
     * 1、24小时上架应用健康度
     * 形式：饼图，初版仅展示不同状态的应用数量，后续再开发点击至具体列表页，初版需支持手动数据库导出明细
     * 应用上架状态说明：
     * （1）正常(即启用)(绿色)：状态为启用的正常上架应用；
     * （2）异常(黄色)：统计24小时内，应用的接口出现超时或网络异常、以及中台内部应用抛出堆栈错误，都需捕获持久化，日志服务定时轮询扫描发现后，将该应用状态置为异常，仅能通过开发者或运营者手动变更为启用。
     * （3）停用(红色)：上架应用的开发者手动可以手动停用，此时网关认证是无法通过的，应用对接肯定会报错（应用对接状态的监控也会关联并置为异常），所以用红色展示，需手动启用或合规删除才能变更此状态；
     *
     * @return
     */
    @Override
    public R<Map<String, Object>> queryAppDataForDashboard() {
        Map<String, Object> dashboardMap = new HashMap<>();

        List<Map<String, String>> nameListForDashboard = applicationMapper.queryAppNameListForDashboard();
        // 未发布
        List<String> notPublishedStatusAppList = new ArrayList<>();
        // 已发布
        List<String> publishedStatusAppList = new ArrayList<>();
        // 已上架（正常）
        List<String> addedStatusAppList = new ArrayList<>();
        // 异常
        List<String> errorStatusAppList = new ArrayList<>();
        // 停用
        List<String> stopStatusAppList = new ArrayList<>();
        for (Map<String, String> map : nameListForDashboard) {
            String name = map.get("name");
            switch (map.get("is_published")) {
                case Constants.APP_STATUS_IS_NOT_PUBLISHED:
                    notPublishedStatusAppList.add(name);
                    break;
                case Constants.APP_STATUS_IS_PUBLISHED:
                    publishedStatusAppList.add(name);
                    break;
                case Constants.APP_STATUS_IS_ADDED:
                    addedStatusAppList.add(name);
                    break;
                case Constants.APP_STATUS_ERROR:
                    errorStatusAppList.add(name);
                    break;
                case Constants.APP_STATUS_OFF:
                    stopStatusAppList.add(name);
                    break;
                default:
            }
        }

        // 开发者应用总数
        dashboardMap.put("appTotalCount", nameListForDashboard.size());
        // 开发者API总数
        Long apiTotalCount = apiMapper.selectCount(new QueryWrapper<Api>().eq("del_flag", 0));
        dashboardMap.put("apiTotalCount", apiTotalCount);
        // 上架开放应用总数
        dashboardMap.put("publishedAppTotalCount", addedStatusAppList.size() + errorStatusAppList.size());
        // 开发者总数
        List<Object> list = sysUserService.selectUsersByRoleType(Constants.ROLE_DEVELOPER).getData();
        dashboardMap.put("developerTotalCount", null == list ? 0 : list.size());

        // 已发布应用列表和数量
        dashboardMap.put("publishedStatusAppList", publishedStatusAppList);
        dashboardMap.put("publishedStatusAppCount", publishedStatusAppList.size());
        // 正常（已上架）应用列表和数量
        dashboardMap.put("addedStatusAppList", addedStatusAppList);
        dashboardMap.put("addedStatusAppCount", addedStatusAppList.size());
        // 异常应用列表和数量
        dashboardMap.put("errorStatusAppList", errorStatusAppList);
        dashboardMap.put("errorStatusAppCount", errorStatusAppList.size());
        // 停用应用列表和数量
        dashboardMap.put("stopStatusAppList", stopStatusAppList);
        dashboardMap.put("stopStatusAppCount", stopStatusAppList.size());
        return R.success(dashboardMap);
    }

    /**
     * 根据code查询app
     *
     * @param appCode
     * @return
     */
    @Override
    public R<Application> queryAppByAppCode(String appCode) {
        return R.success(applicationMapper.queryAppByCode(appCode));
    }

    /**
     * 查询对接应用时的应用列表（不包含当前应用）
     *
     * @param appCode
     * @param developerId
     * @return
     */
    @Override
    public R<List<Map<String, String>>> queryAppNameAndCodeForLease(String appCode, Integer developerId) {
        List<String> statusList = new ArrayList<>();
        statusList.add(Constants.APP_STATUS_IS_ADDED);
        statusList.add(Constants.APP_STATUS_ERROR);
        if (null != developerId) {
            statusList.add(Constants.APP_STATUS_IS_PUBLISHED);
        }
        return R.success(applicationMapper.queryAppNameAndCodeForLease(appCode, statusList, developerId));
    }

    /**
     * 查询当前开发者所有对接应用
     *
     * @param developerId
     * @return
     */
    @Override
    public R<List<Map<String, Object>>> queryAllAppLeaseByDeveloper(Integer developerId) {
        return R.success(applicationLeaseMapper.queryAllAppLeaseByDeveloper(developerId));
    }

    /**
     * 移除应用对接
     *
     * @param appLesseeCode
     * @param appLessorCode
     * @return
     */
    @Override
    @Deprecated
    public R<Object> removeAppLease(String appLesseeCode, String appLessorCode) {
        ApplicationLease applicationLease = applicationLeaseMapper.selectOne(new QueryWrapper<ApplicationLease>()
                .eq("app_lessee_code", appLesseeCode)
                .eq("app_lessor_code", appLessorCode)
                .eq("del_flag", 0));
        if (null == applicationLease) {
            return R.fail(ResultCodeEnum.APP_LEASE_IS_NOT_EXIST);
        }

        applicationLeaseMapper.deleteById(applicationLease);
        redisService.del("client_id_to_access:" + applicationLease.getClientId());
        return R.success();
    }

    /**
     * 查询调用当前应用的应用编号列表
     *
     * @param lessorAppCode
     * @return
     */
    @Override
    public R<List<String>> queryAppCodeListByLessorAppCode(String lessorAppCode) {
        return R.success(applicationLeaseMapper.queryAppCodeListByLessorAppCode(lessorAppCode));
    }

    /**
     * 通过查询clientId验证ApplicationLease是否存在
     *
     * @param clientId
     * @return
     */
    @Override
    public R<Boolean> verifyClientId(String clientId) {
        // 通过查询clientId验证ApplicationLease是否存在（是否解除对接）
        Long count = applicationLeaseMapper.selectCount(new QueryWrapper<ApplicationLease>()
                .eq("client_id", clientId).eq("del_flag", 0));
        if (count <= 0) {
            log.error(String.format("client_id %s 对接已删除", clientId));
            return R.success(false);
        }

        // 查询应用是否已经停用
        String[] split = null;
        String lesseeAppCode = null;
        String lessorAppCode = null;
        try {
            split = clientId.split("-");
            lesseeAppCode = split[0];
            lessorAppCode = split[1];
        } catch (Exception e) {
            log.error("解析clientId错误: " + clientId);
            return R.fail();
        }
        Long count1 = applicationMapper.selectCount(new QueryWrapper<Application>()
                .in("code", Arrays.asList(lesseeAppCode, lessorAppCode))
                .eq("is_published", Constants.APP_STATUS_OFF)
                .eq("del_flag", 0));
        if (count1 > 0) {
            log.error(String.format("client_id %s 应用已停用", clientId));
            return R.success(false);
        }

        return R.success(true);
    }

    /**
     * 注销解绑客户端token
     *
     * @param appCode
     * @return
     */
    @Override
    public R<Object> revokeClientToken(String appCode) {
        List<String> clientIdList = applicationLeaseMapper.queryClientListByAppCode(appCode);
        for (String clientId : clientIdList) {
            redisService.del("client_id_to_access:" + clientId);
        }
        return R.success();
    }

    /**
     * 查询已订阅应用列表
     *
     * @param developerId 开发者id
     * @param appName
     * @param appCode
     * @param appId
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> querySubscribedAppList(Integer developerId, String appName, String appCode, Integer appId, Integer limit, Integer offset) {
        Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        if (null == userId) {
            return R.fail(ResultCodeEnum.TOKEN_NULL);
        }
        SysClient sysClient = sysUserService.queryClientByUserId(userId).getData();

        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }

//        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(userId);

        // 根据userId 获取 对应的 clientIds
//        List<Integer> clientIds = this.changeUserIdsToClientIds(userIdList);

        List<Object> list = applicationMapper.querySubscribedAppList(userId, appName, appCode, appId, limit, offset, sysClient.getId(), null);
        // 数据集合
        List<Object> appList = (List<Object>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("appList", appList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }

    /**
     * 查看已订阅应用详情
     *
     * @param appCode
     * @return
     */
    @Override
    public R<Object> querySubscribedAppDetail(String appCode) {
        Integer userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        if (null == userId) {
            return R.fail(ResultCodeEnum.TOKEN_NULL);
        }

        Map<String, Object> appDetailMap = applicationMapper.queryAppDetails(appCode);
        if (null == appDetailMap) {
            return R.fail(ResultCodeEnum.APP_IS_NOT_EXIST);
        }

        //        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(userId);
        //        Map<String, String> oAuthInfo = applicationLeaseMapper.queryOAuthInfo(appCode, userIdList);
        //        if (null == oAuthInfo) {
        //            return R.fail(ResultCodeEnum.APP_DEVELOPER_IS_NOT_EXIST);
        //        }
        SysClient sysClient = sysUserService.queryClientByUserId(userId).getData();
        // 加入插件信息
        List<ApplicationPlugin> applicationPlugins = applicationPluginMapper
                .selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                        .eq(ApplicationPlugin::getAppCode, appCode)
                        .eq(ApplicationPlugin::getDelFlag, 0));
        // 添加订阅时间
        Integer clientId = sysClient.getId();
        ApplicationSubscribeDto applicationSubscribe = applicationSubscribeMapper.querySubscribeDate(clientId, appCode);
        appDetailMap.put("subscribeDate", "");
        if (Objects.nonNull(applicationSubscribe)) {
            appDetailMap.put("subscribeDate", applicationSubscribe.getSubscribeDate());
        }
        // 遍历查询Client 相关信息
        applicationPlugins.forEach(a -> {
            //            List<ApplicationPluginClient> applicationPluginClients = applicationPluginClientMapper.queryByAppSubscribe(appCode);
            List<ApplicationPluginClient> applicationPluginClients = applicationPluginClientMapper.selectList(new LambdaQueryWrapper<ApplicationPluginClient>()
                    .eq(ApplicationPluginClient::getAppPluginId, a.getId())
                    .eq(ApplicationPluginClient::getDelFlag, 0)
            );
            if (!CollectionUtils.isEmpty(applicationPluginClients)) {
                a.setApplicationPluginClients(applicationPluginClients);
            }
        });
        appDetailMap.put("plugins", applicationPlugins);

        String urlCode = applicationSubscribe.getAppClientCode();
        appDetailMap.put("gatewayDomain", gatewayDomain + "/" + urlCode);
        //        appDetailMap.put("clientId", oAuthInfo.get("clientId"));
        //        appDetailMap.put("clientSecret", oAuthInfo.get("clientSecret"));
        return R.success(appDetailMap);
    }

    /**
     * 查询产品列表
     *
     * @return
     */
    @Override
    public R<Object> queryProductList() {
        return R.success(productMapper.queryProductList());
    }

    @Override
    public ApplicationNumDTO queryApplicationNum(ApplicationNumVo applicationNumVo) {
        ApplicationNumDTO applicationNumDTO = new ApplicationNumDTO();
        Integer developerId = applicationNumVo.getDeveloperId();
        SysClient sysClient = sysUserService.queryClientByUserId(developerId).getData();
//        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        // 根据userId 获取 对应的 clientIds
//        List<Integer> clientIds = this.changeUserIdsToClientIds(userIdList);
        // 订阅应用数
        List<Object> list = applicationMapper.querySubscribedAppList(developerId, null, null, null, null, null, sysClient.getId(), null);
        Integer subscribedCount = ((List<Integer>) list.get(1)).get(0);
        // 注册应用数
        Integer appCount = applicationMapper.queryAppVoList(developerId, null, null, null, null, null, null, null, null, null, null).size();
        // api 数量
        Long apiCount = apiMapper.selectCount(new LambdaQueryWrapper<Api>().eq(Objects.nonNull(developerId), Api::getCreationBy, developerId).eq(Api::getDelFlag, 0));

        applicationNumDTO.setApplicationNum(appCount).setApiNum(apiCount).setSubscribedNum(subscribedCount);
        return applicationNumDTO;
    }

    @Override
    public Long applicationSubscribeNum(String appCode, Long startTime, Long endTime) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        LambdaQueryWrapper<ApplicationSubscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationSubscribe::getAppSubscribedCode, appCode)
                .between(ApplicationSubscribe::getCreationDate, DateUtil.format(startDate, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(endDate, "yyyy-MM-dd HH:mm:ss"))
                .eq(ApplicationSubscribe::getDelFlag, 0);
        Long aLong = applicationSubscribeMapper.selectCount(wrapper);
        return aLong;
    }


    /**
     * 查询订阅当前应用的应用列表
     *
     * @param appCode
     * @param developerId
     * @return
     */
    @Override
    public R<Object> querySubscribedAppList(String appCode, Integer developerId) {
        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        return R.success(applicationMapper.querySubscribeCurrentAppList(appCode, userIdList));
    }

    @Override
    public List<Integer> changeUserIdsToClientIds(List<Integer> userIds) {
        // 根据userId 获取 对应的 clientIds
        List<Integer> clientIds = userIds.stream()
                //                .map(a -> ((SysClient) sysUserService.queryClientByUserId(a).getData()).getId())
                .map(a -> {
                    SysClient sysClient = sysUserService.queryClientByUserId(a).getData();

                    if (Objects.nonNull(sysClient)) {
                        return sysClient.getId();
                    }
                    return 0;
                })
                .collect(Collectors.toList());
        return clientIds;
    }

    @Override
    @Transactional
    public R<Object> updateAppVersion(Integer appVersionId, ApplicationVersionVo applicationVersionVo) {
        ApplicationVersion applicationVersion = applicationVersionMapper.selectById(appVersionId);
        if (Objects.isNull(applicationVersion)) {
            return R.fail(ResultCodeEnum.APP_VERSION_IS_EXIST);
        }
        if (null != applicationVersionVo.getApiIds()) {
            // 删除api路由
            List<Api> apiList = apiMapper.queryApiListByCondition(applicationVersion.getAppCode(), appVersionId);
            List<Api> orphanApiList = apiService.getApiListNotUsedByOtherAppOrAppVersion(apiList, applicationVersion.getAppId()).getData();
            executorService.execute(() ->
                    appApiGatewayService.deleteApiGatewayConfig(applicationVersion.getAppId(), orphanApiList, null));

            // 删除之前的关联，重新添加
            LambdaQueryWrapper<ApplicationApi> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ApplicationApi::getDelFlag, 0);
            wrapper.eq(ApplicationApi::getAppVersionId, applicationVersion.getId());
            wrapper.eq(ApplicationApi::getAppCode, applicationVersion.getAppCode());
            wrapper.eq(ApplicationApi::getAppId, applicationVersion.getAppId());
            applicationApiMapper.delete(wrapper);
            String[] apiIdList = applicationVersionVo.getApiIds().split(",");
            Arrays.stream(apiIdList).forEach(a -> {
                ApplicationApi applicationApi = new ApplicationApi();
                applicationApi.setApiId(Integer.valueOf(a));
                applicationApi.setAppVersionId(appVersionId);
                applicationApi.setAppId(applicationVersion.getAppId());
                applicationApi.setAppCode(applicationVersion.getAppCode());
                applicationApiMapper.insert(applicationApi);
            });

            // 重新发布订阅相关路由
            appPluginService.reSubscribeApp(applicationVersionVo.getAppCode());

        }
        if (StringUtils.isNotEmpty(applicationVersionVo.getVersionDesc())) {
            applicationVersion.setDescription(applicationVersionVo.getVersionDesc());
        }
        if (StringUtils.isNotEmpty(applicationVersionVo.getAppVersion())) {
            applicationVersion.setVersion(applicationVersionVo.getAppVersion());
        }
        if (StringUtils.isNotEmpty(applicationVersionVo.getMarkdown())) {
            applicationVersion.setMarkdown(applicationVersionVo.getMarkdown());
        }
        applicationVersionMapper.updateById(applicationVersion);
        // 查询服务插件
        List<ApplicationPlugin> plugins = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, applicationVersion.getAppCode())
                .eq(ApplicationPlugin::getEnabled, 1));
        //单独查询是否包含sentinel 插件
        List<ApplicationPlugin> applicationPlugins = plugins.stream().filter(a -> PluginTypeEnum.SENTINEL.getType().equals(a.getPluginType())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(applicationPlugins)) {
            sentinelProvider.addOrRefreshApiGroup(applicationPlugins.get(0).getId() + "");
        }
        return R.success();
    }

    @Override
    public R<Object> deleteAppVersion(Integer appVersionId) {
        ApplicationVersion applicationVersion = applicationVersionMapper.selectById(appVersionId);
        if (Objects.nonNull(applicationVersion)) {
            applicationVersionMapper.deleteById(appVersionId);
            // 查询服务插件
            List<ApplicationPlugin> plugins = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                    .eq(ApplicationPlugin::getAppCode, applicationVersion.getAppCode())
                    .eq(ApplicationPlugin::getEnabled, 1));
            //单独查询是否包含sentinel 插件
            List<ApplicationPlugin> applicationPlugins = plugins.stream().filter(a -> PluginTypeEnum.SENTINEL.getType().equals(a.getPluginType())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(applicationPlugins)) {
                sentinelProvider.addOrRefreshApiGroup(applicationPlugins.get(0).getId() + "");
            }
            // 删除api路由
            List<Api> apiList = apiMapper.queryApiListByCondition(applicationVersion.getAppCode(), appVersionId);
            List<Api> orphanApiList = apiService.getApiListNotUsedByOtherAppOrAppVersion(apiList, applicationVersion.getAppId()).getData();
            executorService.execute(() ->
                    appApiGatewayService.deleteApiGatewayConfig(applicationVersion.getAppId(), orphanApiList, null));

        }
        return R.success();
    }

    @Override
    public R<Object> queryAppVersion(Integer appVersionId) {
        ApplicationVersion applicationVersion = applicationVersionMapper.selectById(appVersionId);
        if (Objects.isNull(applicationVersion)) {
            return R.fail(ResultCodeEnum.APP_VERSION_IS_EXIST);
        }
        // 详情包含 1.关联的所有api  2.关联api 的个数
        List<Integer> apiIds = applicationApiMapper.selectList(new LambdaQueryWrapper<ApplicationApi>()
                        .eq(ApplicationApi::getDelFlag, 0)
                        .eq(ApplicationApi::getAppVersionId, appVersionId)).stream()
                .map(ApplicationApi::getApiId).distinct().collect(Collectors.toList());
        List<Api> apis = apiMapper.selectList(new LambdaQueryWrapper<Api>()
                .eq(Api::getDelFlag, 0)
                .in(!CollectionUtils.isEmpty(apiIds), Api::getId, apiIds));
        ApplicationVersionDto applicationVersionDto = new ApplicationVersionDto().setApplicationVersion(applicationVersion).setApiList(apis);
        return R.success(applicationVersionDto);
    }

    /**
     * 根据订阅编号查询订阅信息
     *
     * @param subscribeCode
     * @return
     */
    @Override
    public R<ApplicationSubscribe> queryAppCodeBySubscribeCode(String subscribeCode) {
        ApplicationSubscribe appSubscribe = applicationSubscribeMapper.selectOne(new LambdaQueryWrapper<ApplicationSubscribe>()
                .eq(ApplicationSubscribe::getAppClientCode, subscribeCode));
        return R.success(appSubscribe);
    }

    @Override
    public R<Object> getMyAppCodes(Integer developId) {
        List<Map<String, Object>> list = applicationMapper.selectList(new LambdaQueryWrapper<Application>()
                        .eq(Application::getCreationBy, developId)
                        .eq(Application::getDelFlag, 0)
                        .orderByDesc(Application::getId))
                .stream().map(a -> {
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("appName", a.getName());
                    data.put("appCode", a.getCode());
                    return data;
                }).collect(Collectors.toList());
        return R.success(list);
    }

    /**
     * 查询鉴权过滤器链
     *
     * @param appCode
     * @return
     */
    @Override
    public R<List<String>> queryAppAuthPluginNameList(String appCode) {
        List<ApplicationPlugin> pluginList = applicationPluginMapper.selectList(new LambdaQueryWrapper<ApplicationPlugin>()
                .eq(ApplicationPlugin::getAppCode, appCode)
                .eq(ApplicationPlugin::getEnabled, 1));
        List<String> pluginNameList = new ArrayList<>();
        pluginList.forEach((plugin) -> pluginNameList.add(plugin.getPluginType()));
        return R.success(pluginNameList);
    }

}
