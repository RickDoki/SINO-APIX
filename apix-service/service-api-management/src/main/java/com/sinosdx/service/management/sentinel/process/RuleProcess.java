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
package com.sinosdx.service.management.sentinel.process;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.sinosdx.service.management.sentinel.entity.LimitInfo;
import com.sinosdx.service.management.sentinel.entity.RuleDefinitionEntity;
import com.sinosdx.service.management.sentinel.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenjian
 * @create 2021-12-30 16:45
 * @Description
 */
@Component
public class RuleProcess {

    @Autowired
    @Qualifier("nacosConfigService")
    ConfigService configService;

    @Autowired
    private RuleRepository repository;

    @Autowired
    @Qualifier("ruleEncoder")
    private Converter<List<RuleDefinitionEntity>, String> encoderConverter;

    @Autowired
    @Qualifier("ruleDecoder")
    private Converter<String, List<RuleDefinitionEntity>> decoderConverter;

    private final String dataId = "sentinel-gw-rule";

    private final String groupId = "SENTINEL_GROUP";

    //限流开关标识
    private static final String CLOSE_PREFIX="closed-";

    //服务限流的标识
    private static final String RESOURE_SUFFIX="-routeId";

    public void initRule(){
        ruleProvider();
    }

    public Map<String,List<LimitInfo>> getAllRules() {
        List<RuleDefinitionEntity> allRule = repository.getAllRule();
        Map<String,List<LimitInfo>> result = new HashMap<>();
        List<LimitInfo> listEntity = new ArrayList<>();
        List<String> codeList = new ArrayList<>();
        for(RuleDefinitionEntity rule:allRule){
            codeList.add(rule.getApp());
            listEntity.add(generateLimitInfo(rule));
        }
        for(String code:codeList){
            result.put(code,new ArrayList<>());
        }
        for(LimitInfo entity:listEntity){
            String code = entity.getAppId();
            result.get(code).add(entity);
        }
        return result;
    }

    public List<LimitInfo> getRulesByApp(String appCode) {
        List<RuleDefinitionEntity> allRule = repository.getAllRule();
        List<LimitInfo> listEntity = new ArrayList<>();
        for (RuleDefinitionEntity rule : allRule) {
            if (rule.getApp().equals(appCode)) {
                listEntity.add(generateLimitInfo(rule));
            }
        }
        return listEntity;
    }

    private LimitInfo generateLimitInfo(RuleDefinitionEntity rule){
        String res =rule.getResource();
        LimitInfo limitInfo = new LimitInfo();
        limitInfo.setAppId(rule.getApp());
        if(res.endsWith(RESOURE_SUFFIX)){
            limitInfo.setPath("");
        }else{
            res = res.startsWith(CLOSE_PREFIX)?res.substring(CLOSE_PREFIX.length()):res;
            limitInfo.setPath(res.substring(rule.getApp().length()));
        }
        limitInfo.setCount(rule.getCount());
        limitInfo.setInterval(rule.getInterval());
        limitInfo.setIntervalUnit(rule.getIntervalUnit());
        limitInfo.setControlBehavior(rule.getControlBehavior());
        limitInfo.setMaxQueueingTimeoutMs(rule.getMaxQueueingTimeoutMs());
        return limitInfo;
    }

    /**
     * 新增 rule 到内存，并保存到 nacos
     * @param ruleList
     */
    public void save(List<RuleDefinitionEntity> ruleList) {
        String appId = ruleList.get(0).getApp();
        repository.delOldRule(appId);
        repository.saveRuleToMem(ruleList);
        rulePublisher(repository.getAllRule());
    }


    /**
     * 发布 rule 信息 到 nacos
     * @param listEntity
     */
    private void rulePublisher(List<RuleDefinitionEntity> listEntity){
        try {
            configService.publishConfig(dataId, groupId, encoderConverter.convert(listEntity));
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 nacos 拉取 rule 信息
     * @return
     */
    private void ruleProvider(){
        String listEntity = "";
        try {
            listEntity = configService.getConfig(dataId, groupId,3000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        if (StringUtil.isEmpty(listEntity)) {
            return;
        }
        List<RuleDefinitionEntity> convert = decoderConverter.convert(listEntity);
        repository.saveRuleFromNacos(convert);
    }

    //关闭指定服务的限流
    public void close(String appId) {
        List<RuleDefinitionEntity> allRule = repository.getAllRule();
        List<RuleDefinitionEntity> close = new ArrayList<>();
        for(RuleDefinitionEntity rule:allRule){
            if(rule.getApp().equals(appId)){
                if(rule.getResource().startsWith(CLOSE_PREFIX)){
                    continue;
                }
                rule.setResource(CLOSE_PREFIX+rule.getResource());
                close.add(rule);
            }
        }
        if(close.size() == 0){
            return;
        }
        repository.delOldRule(appId);
        repository.saveRuleToMem(close);
        rulePublisher(repository.getAllRule());
    }

    //开启指定服务的限流
    public void open(String appId) {
        List<RuleDefinitionEntity> allRule = repository.getAllRule();
        List<RuleDefinitionEntity> open = new ArrayList<>();
        for(RuleDefinitionEntity rule:allRule){
            if(rule.getApp().equals(appId)){
                if(!rule.getResource().startsWith(CLOSE_PREFIX)){
                    continue;
                }
                rule.setResource(rule.getResource().substring(CLOSE_PREFIX.length()));
                open.add(rule);
            }
        }
        if(open.size() == 0){
            return;
        }
        repository.delOldRule(appId);
        repository.saveRuleToMem(open);
        rulePublisher(repository.getAllRule());
    }
}


