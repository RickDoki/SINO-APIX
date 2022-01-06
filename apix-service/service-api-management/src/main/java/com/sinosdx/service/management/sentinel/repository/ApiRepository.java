package com.sinosdx.service.management.sentinel.repository;

import com.sinosdx.service.management.sentinel.entity.ApiDefinitionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shenjian
 * @create 2021-12-31 9:34
 * @Description 将 api 保存在内存
 */
@Component
public class ApiRepository {


    private Map<String, ApiDefinitionEntity> repository = new ConcurrentHashMap<>(16);


    /**
     * 新增的 api分组信息 保存到内存
     * @param listEntity
     * @return
     */
    public String saveApiToMem(List<ApiDefinitionEntity> listEntity){
        if(listEntity.size() == 0){
            return "ok";
        }
        String appId = listEntity.get(0).getApp();
        String apiName;
        Set<String> apiNameSet = repository.keySet();
        for(ApiDefinitionEntity entity : listEntity){//若存在相同api路径，判断是否同一个 app 下
            apiName = entity.getApiName();
            if(apiNameSet.contains(apiName)){
                if(!repository.get(apiName).getApp().equals(appId)){
                    return apiName+"a路径冲突，产生冲突的appId:"+repository.get(apiName).getApp();//返回相同的api路径
                }
            }
        }
        for(ApiDefinitionEntity entity : listEntity){//判断通过，进行新增 api
            apiName = entity.getApiName();
            repository.put(apiName,entity);
        }
        return "ok";
    }

    /**
     * 根据 appId ，删除旧的 api分组信息
     * @param appId
     */
    public void delOldApi(String appId){
        Set<String> keySet = repository.keySet();
        String app;
        for(String key : keySet){
            app = repository.get(key).getApp();
            if(app.equals(appId)){
                repository.remove(key);
            }
        }
    }

    /**
     * 从 nacos 拉取的 api分组信息 保存到内存
     * @param listEntity
     */
    public void saveApiFromNacos(List<ApiDefinitionEntity> listEntity){
        repository.clear();
        String apiName;
        for(ApiDefinitionEntity entity:listEntity){
            apiName = entity.getApiName();
            repository.put(apiName,entity);
        }
    }

    /**
     * 获取所有的 api分组信息 保持到 nacos
     * @return
     */
    public List<ApiDefinitionEntity> getAllApi(){
        List<ApiDefinitionEntity> list = new ArrayList<>();
        list.addAll(repository.values());
        return list;
    }
}
