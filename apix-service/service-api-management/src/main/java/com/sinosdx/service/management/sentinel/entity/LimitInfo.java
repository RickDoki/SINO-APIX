package com.sinosdx.service.management.sentinel.entity;

/**
 * @author shenjian
 * @create 2021-12-31 11:27
 * @Description
 */
public class LimitInfo {

    //限流类型 0-app 1-api
    //private Integer limitType;

    //app code app id
    private String appId;

    //网关路由id
//    private String routeId;

    //api限流时， api路径  path为空时，代表是整个app的限流规则
    private String path;

    //限流次数
    private Integer count;

    //限流间隔
    private Long interval;

    //限流时间单位 0-秒 1-分 2-时 3-天
    private Integer intervalUnit;

    public LimitInfo() {
    }

    public LimitInfo(/*Integer limitType,*/ String appId, /*String routeId,*/ String path, Integer count, Long interval, Integer intervalUnit) {
//        this.limitType = limitType;
        this.appId = appId;
//        this.routeId = routeId;
        this.path = path;
        this.count = count;
        this.interval = interval;
        this.intervalUnit = intervalUnit;
    }

//    public Integer getLimitType() {
//        return limitType;
//    }
//
//    public void setLimitType(Integer limitType) {
//        this.limitType = limitType;
//    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

//    public String getRouteId() {
//        return routeId;
//    }

//    public void setRouteId(String routeId) {
//        this.routeId = routeId;
//    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "LimitInfo{" +
//                "limitType='" + limitType + '\'' +
                ", appId='" + appId + '\'' +
//                ", routeId='" + routeId + '\'' +
                ", path='" + path + '\'' +
                ", count=" + count +
                ", interval=" + interval +
                ", intervalUnit=" + intervalUnit +
                '}';
    }


    public RuleDefinitionEntity generateRule(){
        RuleDefinitionEntity entity = new RuleDefinitionEntity();

        switch(this.getIntervalUnit()){
            case 0:
                entity.setIntervalSec(this.getInterval());
                break;
            case 1:
                entity.setIntervalSec(this.getInterval() * 60);
                break;
            case 2:
                entity.setIntervalSec(this.getInterval() * 60 * 60);
                break;
            case 3:
                entity.setIntervalSec(this.getInterval() * 60 * 60 * 24);
                break;
            default:
                entity.setIntervalSec(this.getInterval());
        }
        entity.setApp(this.getAppId());
//        entity.setResource(this.getLimitType() == 0 ? this.getRouteId():this.getPath());
        entity.setResource(this.getAppId()+this.getPath());
        entity.setCount(this.getCount());
        entity.setInterval(this.getInterval());
        entity.setIntervalUnit(this.getIntervalUnit());
//        entity.setResourceMode(this.getLimitType());
        entity.setResourceMode(1);
        entity.setGrade(1);
        entity.setControlBehavior(0);
        entity.setBurst(0);
        entity.setMaxQueueingTimeoutMs(1000);
        return entity;
    }

}
