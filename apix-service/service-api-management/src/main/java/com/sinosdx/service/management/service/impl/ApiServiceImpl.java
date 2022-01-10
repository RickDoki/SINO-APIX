package com.sinosdx.service.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.consumer.GatewayServiceFeign;
import com.sinosdx.service.management.consumer.SysUserServiceFeign;
import com.sinosdx.service.management.controller.dto.ApplicationVersionDto;
import com.sinosdx.service.management.controller.vo.ApiVersionVo;
import com.sinosdx.service.management.controller.vo.ApiVo;
import com.sinosdx.service.management.dao.entity.*;
import com.sinosdx.service.management.dao.mapper.*;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.ApiService;
import com.sinosdx.service.management.utils.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

    @Resource
    private ApiMapper apiMapper;

    @Resource
    private ApplicationApiMapper applicationApiMapper;

//    @Resource
//    private ApiVersionMapper apiVersionMapper;

    @Autowired
    private GatewayServiceFeign gatewayService;

    @Autowired
    private ApiTemplateMapper apiTemplateMapper;

    @Autowired
    private SysUserServiceFeign sysUserService;

    @Autowired
    private ApplicationVersionMapper applicationVersionMapper;

    /**
     * 创建API
     *
     * @param api
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> createApi(Api api) {
        String domain = api.getDomain();
        if (StringUtils.isAnyEmpty(api.getName(), api.getUrl(), domain)) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }

        // 判断服务地址合法性
        if (Character.isDigit(domain.charAt(0)) && !Pattern.matches(Constants.REGEX_IP, domain)) {
            return R.fail(ResultCodeEnum.SERVER_ADDRESS_IS_WRONG);
        }

        // 处理uri路径开头无"/"
        String url = api.getUrl();
        if (!url.startsWith("/")) {
            api.setUrl("/" + url);
        }

        // 判断api是否已存在
        Long count1 = apiMapper.selectCount(new QueryWrapper<Api>()
                .eq("url", api.getUrl())
                .eq("request_method", api.getRequestMethod())
                .eq("prefix_path", api.getPrefixPath())
                .eq("domain", domain)
                .eq("version", api.getVersion())
                .eq("del_flag", 0));
        if (count1 > 0) {
            return R.fail(ResultCodeEnum.API_IS_EXIST);
        }

        api.setIsPublished(Constants.API_IS_NOT_PUBLISHED);
        api.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        api.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        api.setCreationByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        apiMapper.insert(api);

//        ApiVersion apiVersion = new ApiVersion(api);
//        apiVersion.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
//        apiVersion.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
//        apiVersion.setCreationByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
//        apiVersionMapper.insert(apiVersion);

        return R.success(api);
    }

    /**
     * 修改API
     *
     * @param apiVo
     * @return
     */
    @Override
    public R<Object> updateApi(ApiVo apiVo) {
        Api oldApi = apiMapper.selectById(apiVo.getApiId());
        if (null == oldApi) {
            return R.fail(ResultCodeEnum.API_IS_NOT_EXIST);
        }

        if (StringUtils.isNotEmpty(apiVo.getApiUrl())) {
            oldApi.setUrl(apiVo.getApiUrl());
        }
        if (StringUtils.isNotEmpty(apiVo.getDomain())) {
            oldApi.setDomain(apiVo.getDomain());
        }
        if (StringUtils.isNotEmpty(apiVo.getApiName())) {
            oldApi.setName(apiVo.getApiName());
        }
        if (StringUtils.isNotEmpty(apiVo.getApiVersion())) {
            oldApi.setVersion(apiVo.getApiVersion());
        }
        if (StringUtils.isNotEmpty(apiVo.getDescription())) {
            oldApi.setDescription(apiVo.getDescription());
        }
        if (StringUtils.isNotEmpty(apiVo.getMarkdown())) {
            oldApi.setMarkdown(apiVo.getMarkdown());
        }
        if (StringUtils.isNotEmpty(apiVo.getIsPublished())) {
            oldApi.setIsPublished(apiVo.getIsPublished());
        }
        if (StringUtils.isNotEmpty(apiVo.getRequestMethod())) {
            oldApi.setRequestMethod(apiVo.getRequestMethod());
        }
        if (StringUtils.isNotEmpty(apiVo.getRequestParams())) {
            oldApi.setRequestParams(apiVo.getRequestParams());
        }
        if (StringUtils.isNotEmpty(apiVo.getRequestExample())) {
            oldApi.setRequestExample(apiVo.getRequestExample());
        }
        if (StringUtils.isNotEmpty(apiVo.getResponseExample())) {
            oldApi.setResponseExample(apiVo.getResponseExample());
        }

        // 判断修改后的api是否重复
        Long count1 = apiMapper.selectCount(new QueryWrapper<Api>()
                .eq("url", oldApi.getUrl()).eq("request_method", oldApi.getRequestMethod())
                .eq("version", oldApi.getVersion()).eq("del_flag", 0));
        if (count1 > 0) {
            return R.fail(ResultCodeEnum.API_IS_EXIST);
        }
//        Integer count2 = apiMapper.selectCount(new QueryWrapper<Api>()
//                .eq("url", oldApi.getUrl()).eq("name", oldApi.getName())
//                .eq("version", oldApi.getVersion()).eq("del_flag", 0));
//        if (count2 > 0) {
//            return R.fail(ResultCodeEnum.API_IS_EXIST);
//        }

        oldApi.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        oldApi.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        oldApi.setLastUpdatedByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
        apiMapper.updateById(oldApi);
        return R.success(oldApi);
    }

    /**
     * 删除API
     *
     * @param apiId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> deleteApi(Integer apiId) {
        Long count = applicationApiMapper.selectCount(new QueryWrapper<ApplicationApi>()
                .eq("api_id", apiId).eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.API_IS_BE_USED);
        }

        apiMapper.deleteById(apiId);

//        apiVersionMapper.delete(new QueryWrapper<ApiVersion>()
//                .eq("api_id", apiId).eq("del_flag", 0));
        return R.success();
    }

    /**
     * 查询API列表
     *
     * @param apiName
     * @param apiUrl
     * @param requestMethod
     * @param apiVersion
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @param developerId
     * @return
     */
    @Override
    public R<Object> queryApiList(String apiName, String apiUrl, String requestMethod, String apiVersion,
                                  Long startTime, Long endTime, Integer limit, Integer offset, Integer developerId) {
        if (null == startTime) {
            startTime = DateUtils.addYears(new Date(), -1).getTime();
        }
        if (null == endTime) {
            endTime = System.currentTimeMillis();
        }
        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        List<Map<String, Object>> apiVoList = apiMapper.queryApiList(
                apiName, apiUrl, requestMethod, apiVersion,
                LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8)),
                LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8)),
                limit, offset == null || limit == null ? null : limit * (offset - 1), developerId, userIdList);
        Map<String, Object> apiListMap = new HashMap<>();
        apiListMap.put("apiList", apiVoList);
        List<Map<String, Object>> apiVos = apiMapper.queryApiList(
                apiName, apiUrl, requestMethod, apiVersion,
                LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8)),
                LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8)),
                null, null, developerId, userIdList);
        apiListMap.put("total", apiVos.size());
        return R.success(apiListMap);
    }

    /**
     * 创建api新版本
     *
     * @param apiVersion
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> addNewApiVersion(ApiVersion apiVersion) {
//        Api api = apiMapper.selectOne(new QueryWrapper<Api>()
//                .eq("id", apiVersion.getApiId()).eq("del_flag", 0));
//        if (null == api) {
//            return R.fail(ResultCodeEnum.API_IS_NOT_EXIST);
//        }
//
//        if (StringUtils.isAnyEmpty(apiVersion.getApiName(), apiVersion.getUrl(), apiVersion.getVersion(), apiVersion.getDomain())) {
//            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
//        }

//        Long count = apiVersionMapper.selectCount(new QueryWrapper<ApiVersion>()
//                .eq("api_id", apiVersion.getApiId()).eq("version", apiVersion.getVersion())
//                .eq("del_flag", 0));
//        if (count > 0) {
//            return R.fail(ResultCodeEnum.API_VERSION_IS_EXIST);
//        }

//        apiVersion.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
//        apiVersion.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
//        apiVersion.setCreationByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
//        apiVersionMapper.insert(apiVersion);

        // 修改api最新版本号
//        api = new Api(apiVersion);
//        api.setVersion(apiVersion.getVersion());
//        api.setUrl(apiVersion.getUrl());
//        api.setRequestMethod(apiVersion.getRequestMethod());
//        api.setDescription(apiVersion.getDescription());
//        api.setMarkdown(apiVersion.getMarkdown());
//        api.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
//        api.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
//        api.setLastUpdatedByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
//        apiMapper.updateById(api);

        return R.success();
    }

    /**
     * 修改Api历史版本
     *
     * @param apiVersionVo
     * @return
     */
    @Override
    public R<Object> modifyApiVersion(ApiVersionVo apiVersionVo) {
//        ApiVersion oldApiVersion = apiVersionMapper.selectById(apiVersionVo.getApiVersionId());
//        if (null == oldApiVersion) {
//            return R.fail(ResultCodeEnum.API_IS_NOT_EXIST);
//        }
//
//        if (StringUtils.isNotEmpty(apiVersionVo.getUrl())) {
//            oldApiVersion.setUrl(apiVersionVo.getUrl());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getApiName())) {
//            oldApiVersion.setApiName(apiVersionVo.getApiName());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getVersion())) {
//            oldApiVersion.setVersion(apiVersionVo.getVersion());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getDescription())) {
//            oldApiVersion.setDescription(apiVersionVo.getDescription());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getMarkdown())) {
//            oldApiVersion.setMarkdown(apiVersionVo.getMarkdown());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getIsPublished())) {
//            oldApiVersion.setIsPublished(apiVersionVo.getIsPublished());
//        }
//        if (StringUtils.isNotEmpty(apiVersionVo.getRequestMethod())) {
//            oldApiVersion.setRequestMethod(apiVersionVo.getRequestMethod());
//        }
//
//        oldApiVersion.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
//        oldApiVersion.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
//        oldApiVersion.setLastUpdatedByUsername(ThreadContext.get(Constants.THREAD_CONTEXT_USERNAME));
//        apiVersionMapper.updateById(oldApiVersion);
//        return R.success(oldApiVersion);
        return R.success();
    }

    /**
     * 查询api模板列表
     *
     * @param name
     * @param url
     * @param requestMethod
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryApiTemplateList(String name, String url, String requestMethod, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }
        List<Object> list = apiTemplateMapper.queryApiTemplateByCondition(name, url, requestMethod, limit, offset);
        // 数据集合
        List<ApiTemplate> apiTemplateList = (List<ApiTemplate>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("templateList", apiTemplateList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }

    /**
     * 查看api模板
     *
     * @param id
     * @return
     */
    @Override
    public R<Object> getApiTemplate(Integer id) {
        ApiTemplate apiTemplate = apiTemplateMapper.selectById(id);
        if (null == apiTemplate) {
            return R.fail(ResultCodeEnum.API_TEMPLATE_IS_NOT_EXIST);
        }
        return R.success(apiTemplate);
    }

    /**
     * 查看api详情
     *
     * @param apiId
     * @return
     */
    @Override
    public R<Object> queryApiDetail(Integer apiId) {
//        return R.success(apiMapper.selectById(apiId));
        // 默认过滤通用参数,自己写sql
        return R.success(apiMapper.getApiDetail(apiId));
    }

    @Override
    public R<Object> queryApiListByAppVersionId(String appCode, Integer appVersionId) {
        ApplicationVersion applicationVersion = applicationVersionMapper.selectById(appVersionId);
        List<ApplicationApi> applicationApis = applicationApiMapper.selectList(new LambdaQueryWrapper<ApplicationApi>()
                .eq(ApplicationApi::getAppCode, appCode)
                .eq(ApplicationApi::getAppVersionId, appVersionId)
                .eq(ApplicationApi::getDelFlag, 0)
        );
        List<Api> apiList = applicationApis.stream().map(a -> apiMapper.selectById(a.getApiId())).collect(Collectors.toList());
        ApplicationVersionDto applicationVersionDto = new ApplicationVersionDto().setApiList(apiList).setApplicationVersion(applicationVersion);
        return R.success(applicationVersionDto);
    }
}
