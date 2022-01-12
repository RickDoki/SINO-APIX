package com.sinosdx.service.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.management.consumer.GatewayServiceFeign;
import com.sinosdx.service.management.dao.entity.Api;
import com.sinosdx.service.management.dao.entity.ApplicationApiGateway;
import com.sinosdx.service.management.dao.entity.ApplicationPluginClient;
import com.sinosdx.service.management.dao.mapper.ApplicationApiGatewayMapper;
import com.sinosdx.service.management.dao.mapper.ApplicationPluginClientMapper;
import com.sinosdx.service.management.enums.PluginTypeEnum;
import com.sinosdx.service.management.service.AppApiGatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wendy
 * @date 2022/1/12
 */
@Service
@Slf4j
public class AppApiGatewayServiceImpl implements AppApiGatewayService {

    @Resource
    private ApplicationApiGatewayMapper appApiGatewayMapper;

    @Autowired
    private GatewayServiceFeign gatewayService;

    @Resource
    private ApplicationPluginClientMapper applicationPluginClientMapper;

    /**
     * api发布到网关
     *
     * @param applicationId
     * @param apiList
     */
    @Override
    public void updateGatewayConfig(Integer applicationId, List<Api> apiList, String appClientCode) {
        //        String urlCode = StringUtils.isEmpty(application.getProductId()) ? application.getCode() : application.getProductId();

        // 先将所有新增的路由入库（应用方）
        Set<String> addGatewayIdSet = new HashSet<>();
        Set<String> updateGatewayIdSet = new HashSet<>();
        for (Api api : apiList) {
            String uri = "/" + appClientCode + api.getUrl();
            while (uri.contains("{")) {
                String bracket = uri.substring(uri.indexOf("{"), uri.indexOf("}") + 1);
                uri = uri.replace(bracket, "*");
            }

            // 查询网关当前应用服务地址的路由列表
            List<ApplicationApiGateway> gatewayList = appApiGatewayMapper.selectList(new QueryWrapper<ApplicationApiGateway>()
                    .eq("url_code", appClientCode).eq("domain", api.getDomain())
                    .eq("is_internal", api.getIsInternal()).eq("prefix_path", api.getPrefixPath()));
            String gatewayId = "";
            // 区分是新增的路由还是更新的路由
            if (CollectionUtils.isEmpty(gatewayList)) {
                gatewayId = appClientCode + "_" + UUID.randomUUID().toString().split("-")[0];
                addGatewayIdSet.add(gatewayId);
            } else {
                gatewayId = gatewayList.get(0).getGatewayId();
                updateGatewayIdSet.add(gatewayId);
            }
            // 查询网关是否有对应路由
            ApplicationApiGateway appApiGateway = appApiGatewayMapper.selectOne(new QueryWrapper<ApplicationApiGateway>()
                    .eq("url_code", appClientCode).eq("domain", api.getDomain())
                    .eq("app_id", applicationId).eq("api_url", api.getUrl())
                    .eq("is_internal", api.getIsInternal()).eq("prefix_path", api.getPrefixPath()));
            if (null == appApiGateway) {
                ApplicationApiGateway gateway = ApplicationApiGateway.builder()
                        .urlCode(appClientCode)
                        .appId(applicationId)
                        .apiId(api.getId())
                        .apiUrl(api.getUrl())
                        .prefixPath(api.getPrefixPath())
                        .domain(api.getDomain())
                        .gatewayId(gatewayId)
                        .isInternal(api.getIsInternal())
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

        // 查询服务插件配置
        List<ApplicationPluginClient> appPluginClients = applicationPluginClientMapper.queryByAppSubscribe(gatewayList.get(0).getUrlCode());

        // 配置过滤器
        if (!appPluginClients.isEmpty()) {
            for (ApplicationPluginClient appPluginClient : appPluginClients) {
                Map<String, Object> gatewayFilter = new HashMap<>();
                Map<String, Object> filterArgs = new HashMap<>();
                JSONObject pluginParams = JSONObject.parseObject(appPluginClient.getPluginParams());
                // jwt插件
                if (appPluginClient.getPluginType().equals(PluginTypeEnum.JWT.getType())) {
                    gatewayFilter.put("name", PluginTypeEnum.JWT.getFilterName());
                    filterArgs.put("_genkey_0", pluginParams.getString("keyName"));
                    filterArgs.put("_genkey_1", pluginParams.getString("secretKey"));
                    filterArgs.put("_genkey_2", pluginParams.getLong("expirationTime"));
                    gatewayFilter.put("args", filterArgs);
                }
                // oauth2插件
                else if (appPluginClient.getPluginType().equals(PluginTypeEnum.OAUTH2.getType())) {
                    gatewayFilter.put("name", PluginTypeEnum.OAUTH2.getFilterName());
                    filterArgs.put("_genkey_0", pluginParams.getString("clientId"));
                    filterArgs.put("_genkey_1", pluginParams.getString("clientSecret"));
                    gatewayFilter.put("args", filterArgs);
                }
                // base_auth插件
                else if (appPluginClient.getPluginType().equals(PluginTypeEnum.BASE_AUTH.getType())) {
                    gatewayFilter.put("name", PluginTypeEnum.BASE_AUTH.getFilterName());
                    filterArgs.put("_genkey_0", pluginParams.getString("username"));
                    filterArgs.put("_genkey_1", pluginParams.getString("password"));
                    gatewayFilter.put("args", filterArgs);
                } else {
                    continue;
                }
                filterList.add(gatewayFilter);
            }
        }
        gatewayConfig.put("filters", filterList);

        return gatewayConfig;
    }
}
