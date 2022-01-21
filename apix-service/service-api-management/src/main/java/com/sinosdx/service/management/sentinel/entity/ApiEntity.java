package com.sinosdx.service.management.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenjian
 * @create 2021-12-30 23:45
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiEntity {

    //匹配规则  0-精确匹配 1-前缀匹配 2-正则匹配
    private Integer matchStrategy;

    //api 路径信息
    private String pattern;

}
