package com.sinosdx.service.management.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenjian
 * @create 2021-12-31 11:27
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    //流控方式 0-快速失败 2-匀速排队
    private Integer controlBehavior;


    //匀速排队-最大时间/毫秒
    private Integer maxQueueingTimeoutMs;

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
        entity.setControlBehavior(this.getControlBehavior());//0
        entity.setBurst(0);
        entity.setMaxQueueingTimeoutMs(this.maxQueueingTimeoutMs);//1000
        return entity;
    }

}
