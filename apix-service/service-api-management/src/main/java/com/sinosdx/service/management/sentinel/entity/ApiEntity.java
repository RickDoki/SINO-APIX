package com.sinosdx.service.management.sentinel.entity;

/**
 * @author shenjian
 * @create 2021-12-30 23:45
 * @Description
 */
public class ApiEntity {

    //匹配规则  0-精确匹配 1-前缀匹配 2-正则匹配
    private Integer matchStrategy;

    //api 路径信息
    private String pattern;

    public ApiEntity() {
    }

    public ApiEntity(Integer matchStrategy, String pattern) {
        this.matchStrategy = matchStrategy;
        this.pattern = pattern;
    }

    public Integer getMatchStrategy() {
        return matchStrategy;
    }

    public void setMatchStrategy(Integer matchStrategy) {
        this.matchStrategy = matchStrategy;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "ApiEntity{" +
                "matchStrategy=" + matchStrategy +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
