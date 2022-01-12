package com.sinosdx.service.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.dao.entity.Application;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.dao.entity.ApplicationPluginClient;
import com.sinosdx.service.management.dao.mapper.ApplicationMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationPluginClientMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationPluginMapper;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.AppPluginService;
import com.sinosdx.service.management.utils.ThreadContext;
import com.sinosdx.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

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

        applicationPlugin.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        applicationPlugin.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        applicationPluginMapper.updateById(applicationPlugin);

        if (applicationPlugin.getEnabled() == 0) {
            redisService.setRemove(Constants.REDIS_PREFIX_APP_PLUGIN + applicationPlugin.getAppCode(), applicationPlugin.getPluginType());
        } else if (applicationPlugin.getEnabled() == 1) {
            redisService.sSet(Constants.REDIS_PREFIX_APP_PLUGIN + applicationPlugin.getAppCode(), applicationPlugin.getPluginType());
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
        if (Objects.nonNull(applicationPlugin)) {
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
}
