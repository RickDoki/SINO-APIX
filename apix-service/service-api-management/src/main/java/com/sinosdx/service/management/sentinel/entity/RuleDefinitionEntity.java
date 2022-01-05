package com.sinosdx.service.management.sentinel.entity;

/**
 * @author shenjian
 * @create 2021-12-30 15:39
 * @Description 根据app和 resource 确定唯一性
 */
public class RuleDefinitionEntity {

    //app code
    private String app;

    //routeId 或者 api_group_name
    private String resource;

    //限流次数  在线程数模式下-线程数量
    private Integer count;

    //限流间隔-秒 = interval*intervalUnit
    private Long intervalSec;

    //限流间隔
    private Long interval;

    //限流时间单位 0-秒 1-分 2-时 3-天
    private Integer intervalUnit;

    //资源类型 0-routeId 1-api_group_name
    private Integer resourceMode;

    //限流类型 0-线程数 1-QPS
    private Integer grade;

    //流控方式 0-快速失败 2-匀速排队
    private Integer controlBehavior;

    //快速失败-最大突发数 0
    private Integer burst;

    //匀速排队-最大时间/毫秒
    private Integer maxQueueingTimeoutMs;

    public RuleDefinitionEntity() {
    }

    public RuleDefinitionEntity(String app, String resource, Integer count, Long intervalSec,
                                Long interval, Integer intervalUnit, Integer resourceMode,
                                Integer grade, Integer controlBehavior, Integer burst,
                                Integer maxQueueingTimeoutMs) {
        this.app = app;
        this.resource = resource;
        this.count = count;
        this.intervalSec = intervalSec;
        this.interval = interval;
        this.intervalUnit = intervalUnit;
        this.resourceMode = resourceMode;
        this.grade = grade;
        this.controlBehavior = controlBehavior;
        this.burst = burst;
        this.maxQueueingTimeoutMs = maxQueueingTimeoutMs;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getIntervalSec() {
        return intervalSec;
    }

    public void setIntervalSec(Long intervalSec) {
        this.intervalSec = intervalSec;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Integer getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(Integer intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public Integer getResourceMode() {
        return resourceMode;
    }

    public void setResourceMode(Integer resourceMode) {
        this.resourceMode = resourceMode;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getControlBehavior() {
        return controlBehavior;
    }

    public void setControlBehavior(Integer controlBehavior) {
        this.controlBehavior = controlBehavior;
    }

    public Integer getBurst() {
        return burst;
    }

    public void setBurst(Integer burst) {
        this.burst = burst;
    }

    public Integer getMaxQueueingTimeoutMs() {
        return maxQueueingTimeoutMs;
    }

    public void setMaxQueueingTimeoutMs(Integer maxQueueingTimeoutMs) {
        this.maxQueueingTimeoutMs = maxQueueingTimeoutMs;
    }

    @Override
    public String toString() {
        return "GatewayLimitRule{" +
                "app='" + app + '\'' +
                ", resource='" + resource + '\'' +
                ", count=" + count +
                ", intervalSec=" + intervalSec +
                ", interval=" + interval +
                ", intervalUnit=" + intervalUnit +
                ", resourceMode=" + resourceMode +
                ", grade=" + grade +
                ", controlBehavior=" + controlBehavior +
                ", burst=" + burst +
                ", maxQueueingTimeoutMs=" + maxQueueingTimeoutMs +
                '}';
    }
}
