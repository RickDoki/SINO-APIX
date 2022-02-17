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
