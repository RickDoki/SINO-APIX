package com.sinosdx.service.event.bridge.service.impl.process;

import com.github.rholder.retry.*;
import com.sinosdx.service.event.bridge.config.EventBridgeProperties;
import com.sinosdx.service.event.bridge.event.listener.EventProcessRetryListener;
import com.sinosdx.service.event.bridge.service.IEventDeliveryService;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author pengjiahu
 * @date 2020-12-02 16:20
 * @description
 */
@Component
public class RetryBuilder {

    @Lazy
    @Autowired
    private IEventDeliveryService eventDeliveryService;

    @Autowired
    private EventBridgeProperties eventBridgeProperties;

    public Retryer<TargetCallBO> build() {
        /*
           等待策略：每次请求间隔1s
           .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
           等待策略：指数增长,以2s的指数倍增长，最大等待时间是5s
           .withWaitStrategy(WaitStrategies.exponentialWait(2000, 5*1000,TimeUnit.MILLISECONDS))
           等待策略：递增,默认2s,每次在原基础时间上增加5s
           .withWaitStrategy(WaitStrategies.incrementingWait(2000, TimeUnit.MILLISECONDS,5*1000,TimeUnit.MILLISECONDS))
           等待策略：随机区间[2s~10s]
           .withWaitStrategy(WaitStrategies.randomWait(2000, TimeUnit.MILLISECONDS,10*1000,TimeUnit.MILLISECONDS))
           等待策略：斐波拉契数列形式增长,从2s开始增长，最大10s
           .withWaitStrategy(WaitStrategies.fibonacciWait(2000, 10*1000,TimeUnit.MILLISECONDS))
           等待策略：组合,斐波拉契数列+固定1s+随机
           .withWaitStrategy(WaitStrategies.join(
                WaitStrategies.fibonacciWait(2000, 10*1000, TimeUnit.MILLISECONDS),
                WaitStrategies.fixedWait(1000, TimeUnit.MILLISECONDS),
                WaitStrategies.randomWait(2000, TimeUnit.MILLISECONDS,10*1000,TimeUnit.MILLISECONDS)))
         */
        /*
            停止策略：无限次重试 默认
            .withStopStrategy(StopStrategies.neverStop())
            停止策略：重试6次
            .withStopStrategy(StopStrategies.stopAfterAttempt(6))
            停止策略：延迟时间大于10s停止
            .withStopStrategy(StopStrategies.stopAfterDelay(10*1000,TimeUnit.MILLISECONDS))
         */
        return RetryerBuilder.<TargetCallBO>newBuilder()
                //重试条件
                //.retryIfException()
                //.retryIfRuntimeException()
                //.retryIfExceptionOfType(Exception.class)
                //.retryIfException(Predicates.equalTo(new Exception()))
                //.retryIfResult(r -> Objects.requireNonNull(r).getCode() != 200)
                //.retryIfResult(Objects::isNull)
                //.retryIfResult(Predicates.equalTo(false))
                .retryIfExceptionOfType(NeedRetryException.class)
                //等待策略：递增,默认10s,每次在原基础时间上增加5s
                .withWaitStrategy(WaitStrategies
                        .incrementingWait(eventBridgeProperties.getInitialSleepTime(),
                                eventBridgeProperties.getInitialSleepTimeUnit(),
                                eventBridgeProperties.getIncrement(),
                                eventBridgeProperties.getIncrementTimeUnit()))
                //停止策略 : 尝试请求6次
                .withStopStrategy(StopStrategies
                        .stopAfterAttempt(eventBridgeProperties.getStopAfterAttempt()))
                //时间限制 : 某次请求不得超过2s , 类似: TimeLimiter timeLimiter = new SimpleTimeLimiter();
                //.withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(20, TimeUnit.SECONDS))
                //默认的阻塞策略：线程睡眠
                .withBlockStrategy(BlockStrategies.threadSleepStrategy())
                //自定义阻塞策略：自旋锁
                //.withBlockStrategy(new SpinBlockStrategy())
                //自定义重试监听器
                .withRetryListener(new EventProcessRetryListener(eventDeliveryService))
                .build();

    }

}
