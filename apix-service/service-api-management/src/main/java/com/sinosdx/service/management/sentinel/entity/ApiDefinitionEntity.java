package com.sinosdx.service.management.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author shenjian
 * @create 2021-12-30 16:21
 * @Description  根据app code 和 路径名称确定
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiDefinitionEntity {
    //app code
    private String app;

    //自定义 api 名称 -> resource
    private String apiName;

    //保存具体的 api 信息
    private Set<ApiEntity> predicateItems;

}
