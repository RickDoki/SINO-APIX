package com.sinosdx.service.management.sentinel.repository;

import com.sinosdx.service.management.sentinel.entity.RuleDefinitionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shenjian
 * @create 2021-12-31 11:13
 * @Description
 */
@Component
public class RuleRepository {

    private Map<String, RuleDefinitionEntity> repository = new ConcurrentHashMap<>(16);

    public void saveRuleToMem(List<RuleDefinitionEntity> listEntity){
        String resource;
        for(RuleDefinitionEntity entity : listEntity){
            resource = entity.getResource();
            repository.put(resource,entity);
        }
    }

    public void delOldRule(String appCode){
        Set<String> keySet = repository.keySet();
        String app;
        for(String key : keySet){
            app = repository.get(key).getApp();
            if(app.equals(appCode)){
                repository.remove(key);
            }
        }
    }


    public void saveRuleFromNacos(List<RuleDefinitionEntity> listEntity){
        repository.clear();
        String resource;
        for(RuleDefinitionEntity entity:listEntity){
            resource = entity.getResource();
            repository.put(resource,entity);
        }
    }

    public List<RuleDefinitionEntity> getAllRule(){
        List<RuleDefinitionEntity> list = new ArrayList<>();
        list.addAll(repository.values());
        return list;
    }

}
