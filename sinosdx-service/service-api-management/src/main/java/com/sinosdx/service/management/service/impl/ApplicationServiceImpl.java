package com.sinosdx.service.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.consumer.GatewayServiceFeign;
import com.sinosdx.service.management.consumer.OauthClientDetailsServiceFeign;
import com.sinosdx.service.management.consumer.OmpServiceFeign;
import com.sinosdx.service.management.consumer.SysUserServiceFeign;
import com.sinosdx.service.management.controller.dto.ApplicationNumDTO;
import com.sinosdx.service.management.controller.vo.*;
import com.sinosdx.service.management.dao.entity.*;
import com.sinosdx.service.management.dao.mapper.*;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
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

    @Resource
    private ApiVersionMapper apiVersionMapper;

    @Autowired
    private OauthClientDetailsServiceFeign oauthClientDetailsService;

    @Autowired
    private SysUserServiceFeign sysUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GatewayServiceFeign gatewayService;

    @Autowired
    private OmpServiceFeign ompService;

    @Resource
    private ApplicationApiGatewayMapper appApiGatewayMapper;

    @Resource
    private ProductMapper productMapper;

    @Value("${domain.gateway:http://47.103.109.225:30000/api}")
    private String gatewayDomain;

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
        Long existApp = applicationMapper.selectCount(new QueryWrapper<Application>()
                .eq("name", application.getName())
                .or().eq("code", application.getCode())
                .eq("del_flag", 0));
        if (existApp > 0) {
            return R.fail(ResultCodeEnum.APPLICATION_IS_EXIST);
        }
        applicationMapper.insert(application);

        // 插入application_developer
        ApplicationDeveloper applicationDeveloper = new ApplicationDeveloper();
        applicationDeveloper.setAppId(application.getId());
        applicationDeveloper.setAppCode(application.getCode());
        applicationDeveloper.setIsCreator(true);
        applicationDeveloper.setUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        applicationDeveloper.setUserId(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        applicationDeveloper.setPhone(ThreadContext.get(Constants.THREAD_CONTEXT_PHONE));
        applicationDeveloperMapper.insert(applicationDeveloper);

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
            statusList.add(Constants.APP_STATUS_IS_ADDED);
            statusList.add(Constants.APP_STATUS_ERROR);
        }
        List<Map<String, Object>> applicationVos = applicationMapper.queryAppVoList(developerId, appName, appCode,
                appId, isPublished, LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8)),
                LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8)),
                limit, offset == null || limit == null ? null : limit * (offset - 1), statusList, userIdList);
        // 将标签处理为数组返回给前端
        applicationVos.forEach(map -> {
            Object label = map.get("label");
            List<String> labelList = new ArrayList<>();
            if (null != label) {
                labelList = Arrays.stream(String.valueOf(label).split("/")).filter(Objects::nonNull).collect(Collectors.toList());
            }
            map.put("label", labelList);
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
            //            appLastUpdateR = sysUserService.findUserById((Integer) appDetailMap.get("appLastUpdateBy"));
            appLastUpdate = ompService.queryOmpUser(null, (Integer) appDetailMap.get("appLastUpdateBy"), null).getData();
        }
        if (null != appLastUpdate) {
            appDetailMap.put("appLastUpdateUser", appLastUpdate.get("username"));
        } else {
            appDetailMap.put("appLastUpdateUser", "-");
        }

        if (null != developerId) {
            List<Map<String, Object>> usingAppList = applicationMapper.queryUsingAppList(appCode);
            appDetailMap.put("usingAppList", usingAppList);
        }
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
                case Constants.APP_STATUS_IS_PUBLISHED:
                    // 只有未发布和停用状态可以发布
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_NOT_PUBLISHED)
                            || oldApp.getIsPublished().equals(Constants.APP_STATUS_OFF)) {
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                        msg = "启用成功";
                    } else {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    }
                    break;
                case Constants.APP_STATUS_IS_ADDED:
                    // 只有已发布状态可以上架
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_PUBLISHED)) {
                        // 上架前必须发布过应用版本
                        Long count = applicationVersionMapper.selectCount(new QueryWrapper<ApplicationVersion>()
                                .eq("app_code", oldApp.getCode()).eq("del_flag", 0));
                        if (count < 1) {
                            return R.fail(ResultCodeEnum.APP_VERSION_IS_NOT_EXIST);
                        }
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                        msg = "上架成功";
                    } else {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    }
                    break;
                case Constants.APP_STATUS_ERROR:
                    // 只有已发布和已上架可以改为异常
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_PUBLISHED)
                            || oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_ADDED)) {
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                    } else {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    }
                    break;
                case Constants.APP_STATUS_OFF:
                    // 未发布不能停用
                    if (oldApp.getIsPublished().equals(Constants.APP_STATUS_IS_NOT_PUBLISHED)) {
                        return R.fail(ResultCodeEnum.STATUS_MODIFY_ERROR);
                    } else {
                        oldApp.setIsPublished(applicationVo.getIsPublished());
                        msg = "停用成功";
                    }
                    break;
                default:
            }
        }
        oldApp.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        oldApp.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        oldApp.setLastUpdatedByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        applicationMapper.updateById(oldApp);

        // 如果停用应用，需要注销对应客户端token
        if (null != applicationVo.getIsPublished() && Constants.APP_STATUS_OFF.equals(applicationVo.getIsPublished())) {
            revokeClientToken(oldApp.getCode());
        }

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
        Long count = applicationLeaseMapper.selectCount(new QueryWrapper<ApplicationLease>()
                .eq("app_lessee_code", appCode).or().eq("app_lessor_code", appCode).eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.APP_BE_USED_OR_USING_OTHER_APP);
        }
        // 删除应用
        applicationMapper.delete(new QueryWrapper<Application>().eq("code", appCode));
        // 删除应用版本
        applicationVersionMapper.delete(new QueryWrapper<ApplicationVersion>().eq("app_code", appCode));
        // 删除版本api关联
        applicationApiMapper.delete(new QueryWrapper<ApplicationApi>().eq("app_code", appCode));
        // 删除所有开发者
        applicationDeveloperMapper.delete(new QueryWrapper<ApplicationDeveloper>().eq("app_code", appCode));
        // 删除对应客户端认证信息
        oauthClientDetailsService.deleteOAuthClientDetail(appCode);
        revokeClientToken(appCode);
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

        String[] apiVersionIdList = applicationVersionVo.getApiVersionIds().split(",");
        List<ApiVersion> apiVersionList = new ArrayList<>();
        for (String apiVersionIdStr : apiVersionIdList) {
            Integer apiVersionId = Integer.valueOf(apiVersionIdStr);
            ApiVersion apiVersion = apiVersionMapper.selectById(apiVersionId);
            if (null == apiVersion) {
                return R.fail(ResultCodeEnum.API_IS_NOT_EXIST);
            }
            apiVersionList.add(apiVersion);
        }

        // 验证路由是否发布过
        for (ApiVersion apiVersion : apiVersionList) {
            String urlCode = StringUtils.isEmpty(application.getProductId()) ? application.getCode() : application.getProductId();
            ApplicationApiGateway applicationApiGateway = appApiGatewayMapper.selectOne(new QueryWrapper<ApplicationApiGateway>()
                    .eq("url_code", urlCode)
                    .eq("domain", apiVersion.getDomain())
                    .eq("api_url", apiVersion.getUrl()));
            if (null != applicationApiGateway) {
                return R.fail("/" + urlCode + apiVersion.getUrl() + "已被发布到网关，请更换api发布");
            }
        }

        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setVersion(applicationVersionVo.getAppVersion());
        applicationVersion.setAppId(application.getId());
        applicationVersion.setAppCode(applicationVersionVo.getAppCode());
        applicationVersion.setDescription(applicationVersionVo.getVersionDesc());
        applicationVersionMapper.insert(applicationVersion);

        List<ApiVersionVo> apiVersionVoList = new ArrayList<>();
        for (ApiVersion apiVersion : apiVersionList) {
            ApplicationApi applicationApi = new ApplicationApi();
            applicationApi.setAppId(application.getId());
            applicationApi.setAppVersionId(applicationVersion.getId());
            applicationApi.setAppCode(application.getCode());
            applicationApi.setApiId(apiVersion.getApiId());
            applicationApi.setApiVersionId(apiVersion.getId());
            applicationApiMapper.insert(applicationApi);

            apiVersionVoList.add(new ApiVersionVo(apiVersion));
        }

        // 插入网关数据库
        updateGatewayConfig(application, apiVersionList);

        return R.success(new ApplicationVersionVo(applicationVersion, apiVersionVoList));
    }

    /**
     * api发布到网关
     *
     * @param application
     * @param apiVersionList
     */
    private void updateGatewayConfig(Application application, List<ApiVersion> apiVersionList) {
        String urlCode = StringUtils.isEmpty(application.getProductId()) ? application.getCode() : application.getProductId();

        // 先将所有新增的路由入库（应用方）
        Set<String> addGatewayIdSet = new HashSet<>();
        Set<String> updateGatewayIdSet = new HashSet<>();
        for (ApiVersion apiVersion : apiVersionList) {
            String uri = "/" + urlCode + apiVersion.getUrl();
            while (uri.contains("{")) {
                String bracket = uri.substring(uri.indexOf("{"), uri.indexOf("}") + 1);
                uri = uri.replace(bracket, "*");
            }

            // 查询网关当前应用服务地址的路由列表
            List<ApplicationApiGateway> gatewayList = appApiGatewayMapper.selectList(new QueryWrapper<ApplicationApiGateway>()
                    .eq("url_code", urlCode).eq("domain", apiVersion.getDomain())
                    .eq("is_internal", apiVersion.getIsInternal()).eq("prefix_path", apiVersion.getPrefixPath()));
            String gatewayId = "";
            // 区分是新增的路由还是更新的路由
            if (CollectionUtils.isEmpty(gatewayList)) {
                gatewayId = urlCode + "_" + UUID.randomUUID().toString().split("-")[0];
                addGatewayIdSet.add(gatewayId);
            } else {
                gatewayId = gatewayList.get(0).getGatewayId();
                updateGatewayIdSet.add(gatewayId);
            }
            // 查询网关是否有对应路由
            ApplicationApiGateway appApiGateway = appApiGatewayMapper.selectOne(new QueryWrapper<ApplicationApiGateway>()
                    .eq("url_code", urlCode).eq("domain", apiVersion.getDomain())
                    .eq("app_id", application.getId()).eq("api_url", apiVersion.getUrl())
                    .eq("is_internal", apiVersion.getIsInternal()).eq("prefix_path", apiVersion.getPrefixPath()));
            if (null == appApiGateway) {
                ApplicationApiGateway gateway = ApplicationApiGateway.builder()
                        .urlCode(urlCode)
                        .appId(application.getId())
                        .apiId(apiVersion.getApiId())
                        .apiUrl(apiVersion.getUrl())
                        .prefixPath(apiVersion.getPrefixPath())
                        .domain(apiVersion.getDomain())
                        .gatewayId(gatewayId)
                        .isInternal(apiVersion.getIsInternal())
                        .build();
                appApiGatewayMapper.insert(gateway);
            }
        }

        // 整合需要新增和更新的网关路由，发布到网关
        for (String gatewayId : addGatewayIdSet) {
            JSONObject gatewayConfig = createGatewayConfig(gatewayId);
            log.info("create gateway config: " + gatewayConfig.toJSONString());
            gatewayService.create(gatewayConfig);
        }
        for (String gatewayId : updateGatewayIdSet) {
            JSONObject gatewayConfig = createGatewayConfig(gatewayId);
            log.info("update gateway config: " + gatewayConfig.toJSONString());
            gatewayService.update(gatewayConfig);
        }
    }

    private JSONObject createGatewayConfig(String gatewayId) {
        List<ApplicationApiGateway> gatewayList = appApiGatewayMapper.selectList(new QueryWrapper<ApplicationApiGateway>()
                .eq("gateway_id", gatewayId));
        JSONObject gatewayConfig = new JSONObject();
        gatewayConfig.put("id", gatewayId);
        gatewayConfig.put("uri", gatewayList.get(0).getDomain());
        Map<String, Object> predicate = new HashMap<>();
        predicate.put("name", "Path");
        Map<String, String> argsMap = new LinkedHashMap<>();
        for (int i = 0; i < gatewayList.size(); i++) {
            ApplicationApiGateway gateway = gatewayList.get(i);
            argsMap.put("_genkey_" + i, "/" + gateway.getUrlCode() + gateway.getApiUrl());
        }
        predicate.put("args", argsMap);
        gatewayConfig.put("predicates", Collections.singletonList(predicate));

        List<Object> filterList = new LinkedList<>();
        // 配置是否需要截取路径第一段
        Integer isInternal = gatewayList.get(0).getIsInternal();
        if (isInternal == 0) {
            filterList.add(JSONObject.parseObject("{\n" +
                    "            \"name\": \"StripPrefix\",\n" +
                    "            \"args\": {\n" +
                    "                \"_genkey_0\": \"1\"\n" +
                    "            }\n" +
                    "        }\n"));
        }

        // 配置是否需要拼装前置路径
        String prefixPath = gatewayList.get(0).getPrefixPath();
        if (null != prefixPath) {
            filterList.add(JSONObject.parseObject("{\n" +
                    "            \"name\": \"PrefixPath\",\n" +
                    "            \"args\": {\n" +
                    "                \"_genkey_0\": \"" + prefixPath + "\"\n" +
                    "            }\n" +
                    "        }"));
        }

        filterList.add(JSONObject.parseObject("{\n" +
                "   \"name\":\"Authorize\"\n" +
                "}\n"));
        gatewayConfig.put("filters", filterList);

        return gatewayConfig;
    }

    /**
     * 绑定应用服务（使用资源市场应用服务）
     *
     * @param appLesseeCode
     * @param appLessorCode
     * @return
     */
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

        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }

        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(userId);
        List<Object> list = applicationMapper.querySubscribedAppList(userId, appName, appCode, appId, limit, offset, userIdList);
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

        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(userId);
        Map<String, String> oAuthInfo = applicationLeaseMapper.queryOAuthInfo(appCode, userIdList);
        if (null == oAuthInfo) {
            return R.fail(ResultCodeEnum.APP_DEVELOPER_IS_NOT_EXIST);
        }

        String urlCode = StringUtils.isEmpty(appDetailMap.get("productId").toString()) ? appDetailMap.get("appCode").toString() : appDetailMap.get("productId").toString();
        appDetailMap.put("gatewayDomain", gatewayDomain + "/" + urlCode);
        appDetailMap.put("clientId", oAuthInfo.get("clientId"));
        appDetailMap.put("clientSecret", oAuthInfo.get("clientSecret"));

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
        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        // 订阅应用数
        List<Object> list = applicationMapper.querySubscribedAppList(developerId, null, null, null, null, null, userIdList);
        Integer subscribedCount = ((List<Integer>) list.get(1)).get(0);
        // 注册应用数
        Integer appCount = applicationMapper.queryAppVoList(developerId, null, null, null, null, null, null, null, null, null, userIdList).size();
        // api 数量
        Long apiCount = apiMapper.selectCount(new LambdaQueryWrapper<Api>().eq(Api::getCreationBy, developerId).eq(Api::getDelFlag, 0));

        applicationNumDTO.setApplicationNum(appCount).setApiNum(apiCount).setSubscribedNum(subscribedCount);
        return applicationNumDTO;
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
}
