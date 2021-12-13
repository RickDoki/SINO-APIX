package com.sinosdx.service.event.bridge.service.impl.process;

import cn.hutool.core.util.ObjectUtil;
import com.github.rholder.retry.RetryException;
import com.google.common.base.Preconditions;
import com.sinosdx.service.event.bridge.enums.TargetTypeEnum;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import com.sinosdx.service.event.bridge.service.impl.cell.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author pengjiahu
 * @date 2020-09-23 21:27
 * @description
 */
@Slf4j
@Component
public class EventTargetCallProcess {

    @Lazy
    @Autowired
    private RetryBuilder retryBuilder;

    @Autowired
    private IRuleTargetService ruleTargetService;

    @Async
    public void eventTargetCall(TargetCallBO call) {
        log.debug("7.事件进行目标调用,{}", call);
        Preconditions.checkNotNull(call, "目标不能为空");
        Preconditions.checkNotNull(call.getRuleTargetId(), "目标Id不能为空");
        try {
            Integer targetId = call.getRuleTargetId();
            TargetCallProcessBase targetInfo = (TargetCallProcessBase) ruleTargetService
                    .getTargetInfo(call.getTargetType(), targetId);
            call.setTarget(targetInfo.defaultTarget());
            Callable<TargetCallBO> handle = null;
            switch (TargetTypeEnum.formType(call.getTargetType())) {
                case MQ:
                    handle = new ProcessMqCall(call, targetInfo);
                    break;
                case HTTP:
                    handle = new ProcessHttpCall(call, targetInfo);
                    break;
                case SMS:
                    handle = new ProcessSmsCall(call, targetInfo);
                    break;
                case EMAIL:
                    handle = new ProcessEmailCall(call, targetInfo);
                    break;
                case DINGTALK_NOTICE:
                    handle = new ProcessDingTalkNoticeCall(call, targetInfo);
                    break;
                case WORK_WEIXIN_NOTICE:
                    handle = new ProcessWorkWeixinNoticeCall(call, targetInfo);
                    break;
                case INNER_API:
                    handle = new ProcessInnerApiCall(call, targetInfo);
                    break;
                case REDIS:
                    handle = new ProcessRedisCall(call, targetInfo);
                    break;
                default:
                    log.error("时间目标处理错误，未找到对应实现");
                    break;
            }
            if (ObjectUtil.isNotEmpty(handle)) {
                retryBuilder.build().call(handle);
            }
        } catch (ExecutionException e) {
            log.error("事件目标处理异常，ExecutionException", e);
        } catch (RetryException e) {
            log.debug("[{}]事件目标处理多次未成功，RetryException：{}", call.getEventUuid(), e.getMessage());
        }
    }
}
