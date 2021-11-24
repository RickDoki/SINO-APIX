package com.sinosdx.service.event.bridge.config;

import com.sinosdx.common.base.constants.AppConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@Getter
@Setter
@ConfigurationProperties(prefix = EventBridgeProperties.PREFIX)
public class EventBridgeProperties {

    public static final String PREFIX = AppConstant.GLOBAL_NAME_PREFIX + ".event-bridge";

    /**
     * 事件轨迹日志保存，超过50或大于一分钟进行批量保存里事件轨迹日志
     */
    private Integer numElements = 50;

    /**
     * 事件轨迹日志保存，超过50或大于一分钟进行批量保存里事件轨迹日志,
     */
    private Integer duration = 1;

    /**
     * guava重试次数，停止策略 : 尝试请求参数
     */
    private Integer stopAfterAttempt = 2;

    /**
     * 目标保存，事件目标可添加的最大目标数
     */
    private Integer eventTargetNumMax = 10;

    /**
     * 等待策略(递增,默认?s,每次在原基础时间上增加?s),默认休眠时间
     */
    private long initialSleepTime = 20000;

    /**
     * 等待策略(递增,默认?s,每次在原基础时间上增加?s),默认休眠时间单位
     */
    private TimeUnit initialSleepTimeUnit = TimeUnit.MILLISECONDS;

    /**
     * 等待策略(递增,默认?s,每次在原基础时间上增加?s),默认递增时间
     */
    private long increment = 10000;

    /**
     * 等待策略(递增,默认?s,每次在原基础时间上增加?s),默认递增时间单位
     */
    private TimeUnit incrementTimeUnit = TimeUnit.MILLISECONDS;

    /**
     * 平台内置总线
     */
    private String busName = "platform";

    /**
     * 平台内置总线来源
     */
    private String source = "platform";

    /**
     * 平台内置总线类型
     */
    private String type = "component:log:error";
}
