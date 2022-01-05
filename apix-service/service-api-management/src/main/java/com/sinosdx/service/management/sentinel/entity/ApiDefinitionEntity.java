package com.sinosdx.service.management.sentinel.entity;

import java.util.Set;

/**
 * @author shenjian
 * @create 2021-12-30 16:21
 * @Description  根据app code 和 路径名称确定
 */
public class ApiDefinitionEntity {
    //app code
    private String app;

    //自定义 api 名称 -> resource
    private String apiName;

    //保存具体的 api 信息
    private Set<ApiEntity> predicateItems;

    public ApiDefinitionEntity() {
    }

    public ApiDefinitionEntity(String app, String apiName, Set<ApiEntity> predicateItems) {
        this.app = app;
        this.apiName = apiName;
        this.predicateItems = predicateItems;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Set<ApiEntity> getPredicateItems() {
        return predicateItems;
    }

    public void setPredicateItems(Set<ApiEntity> predicateItems) {
        this.predicateItems = predicateItems;
    }

    @Override
    public String toString() {
        return "GatewayApiDefinition{" +
                "app='" + app + '\'' +
                ", apiName='" + apiName + '\'' +
                ", predicateItems=" + predicateItems +
                '}';
    }
}
