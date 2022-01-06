package com.sinosdx.service.management.sentinel;

import com.sinosdx.service.management.dao.mapper.ApplicationMapper;
import com.sinosdx.service.management.sentinel.entity.ApiDefinitionEntity;
import com.sinosdx.service.management.sentinel.entity.ApiEntity;
import com.sinosdx.service.management.sentinel.entity.LimitInfo;
import com.sinosdx.service.management.sentinel.entity.RuleDefinitionEntity;
import com.sinosdx.service.management.sentinel.process.ApiProcess;
import com.sinosdx.service.management.sentinel.process.RuleProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author shenjian
 * @create 2022-01-05 12:09
 * @Description sentinel限流网关 push模式
 */
@Component
@Slf4j
public class SentinelProvider {

    @Resource
    ApplicationMapper applicationMapper;

    @Autowired
    ApiProcess apiProcess;

    @Autowired
    RuleProcess ruleProcess;

    private static final String RESOURE_SUFFIX="-routeId";

    /**
     * 刷新内存中的保存信息
     * 直接从 nacos 修改限流信息后，需要调用这个方法刷新内存中的数据
     */
    public void refresh(){
        apiProcess.initApi();
        ruleProcess.initRule();
    }

    /**
     * 插件开启
     * @param appId
     */
    public void open(String appId){
        ruleProcess.open(appId);
    }

    /**
     * 插件关闭
     * @param appId
     */
    public void close(String appId){
        ruleProcess.close(appId);
    }

    /**
     * 根据app 查询限流规则
     * @param appCode
     * @return
     */
    public List<LimitInfo> getAppRules(String appCode){
        return ruleProcess.getRulesByApp(appCode);
    }

    /**
     * 查询全部的限流规则
     * @return
     */
    public Map<String,List<LimitInfo>> getRules(){
        return ruleProcess.getAllRules();
    }

    /**
     * 前端保存限流规则时调用
     * @param entityList
     */
    public String save(List<LimitInfo> entityList){
        if(entityList.size()==0){
            return "ok";
        }
        String appId = entityList.get(0).getAppId();
        List<RuleDefinitionEntity> ruleList = new ArrayList<>();
        for(LimitInfo entity:entityList){
            if(entity.getPath().isEmpty()){
                entity.setPath(RESOURE_SUFFIX);
            }
            ruleList.add(entity.generateRule());
        }
        String result = addOrRefreshApiGroup(appId);
        if(result.equals("ok")){
            ruleProcess.save(ruleList);
        }
        //ok是成功
        return result;
    }

    /**
     * app的订阅者发生变动时调用, 或者 app下的 api 数量发生变动时
     * @param appId
     */
    public String addOrRefreshApiGroup(String appId){//每次被订阅时，触发
        List<Map<String, String>> apiList = applicationMapper.queryApiByAppId(appId);
        List<String> codeList = applicationMapper.querySubscribeCodeByAppId(appId);
        ApiDefinitionEntity apiDef = generateAllApiInOneGroup(appId, apiList, codeList);
        List<ApiDefinitionEntity> apiDefList = generateSingleApiInOneGroup(appId, apiList, codeList);
        apiDefList.add(apiDef);
        return apiProcess.save(apiDefList);
    }

    private ApiDefinitionEntity generateAllApiInOneGroup(String appId, List<Map<String, String>> apiList,List<String> codeList) {
        ApiDefinitionEntity entity = new ApiDefinitionEntity();
        entity.setApp(appId);
        entity.setApiName(appId+RESOURE_SUFFIX);
        List<ApiEntity> apiGroup = new ArrayList<>();
        for(Map<String, String> map:apiList){
            String path = map.get("path");
            for(String code: codeList){
                ApiEntity apiEntity = new ApiEntity();
                apiEntity.setMatchStrategy(0);
                apiEntity.setPattern("/"+code+path);
                apiGroup.add(apiEntity);
            }
        }
        entity.setPredicateItems(new LinkedHashSet<>(apiGroup));
        return entity;
    }
    private List<ApiDefinitionEntity> generateSingleApiInOneGroup(String appId, List<Map<String, String>> apiList,List<String> codeList){
        List<ApiDefinitionEntity> result = new ArrayList<>();
        for(Map<String, String> apiMap: apiList){
            String path = apiMap.get("path");
            ApiDefinitionEntity entity = new ApiDefinitionEntity();
            entity.setApiName(appId+path);
            entity.setApp(appId);
            List<ApiEntity> apiGroup = new ArrayList<>();
            for(String code: codeList){
                ApiEntity apiEntity = new ApiEntity();
                apiEntity.setPattern("/"+code+path);
                apiEntity.setMatchStrategy(0);
                apiGroup.add(apiEntity);
            }
            entity.setPredicateItems(new LinkedHashSet<>(apiGroup));
            result.add(entity);
        }
        return result;
    }

}
