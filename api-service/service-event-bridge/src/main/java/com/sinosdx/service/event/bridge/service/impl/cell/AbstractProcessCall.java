package com.sinosdx.service.event.bridge.service.impl.cell;

import com.sinosdx.common.tools.exception.StackTraceUtil;
import com.sinosdx.service.event.bridge.enums.TargetTypeEnum;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.impl.process.NeedRetryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public abstract class AbstractProcessCall implements Callable<TargetCallBO> {

    /**
     * 处理过程，模板方法
     *
     * @param targetCall
     * @return
     */
    public TargetCallBO processTargetCall(TargetCallBO targetCall) {
        try {
            log.debug("8.事件进行{}目标调用,{}", TargetTypeEnum
                    .formType(targetCall.getTargetType()).name(), targetCall);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String response = process();
            stopWatch.stop();
            targetCall.setTimeConsumer(stopWatch.getTime(TimeUnit.MILLISECONDS));
            targetCall.setResponse(response);
            return targetCall;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NeedRetryException(StackTraceUtil.getStackTrace(e), targetCall);
        }
    }

    /**
     * 目标处理
     *
     * @return
     */
    public abstract String process();

}
