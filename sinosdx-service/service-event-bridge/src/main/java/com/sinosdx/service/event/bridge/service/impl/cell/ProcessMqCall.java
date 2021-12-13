package com.sinosdx.service.event.bridge.service.impl.cell;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetMq;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@SuppressWarnings("deprecation")
@Slf4j
public class ProcessMqCall extends AbstractProcessCall {

    private final BinderAwareChannelResolver channelResolver = SpringContextHolder
            .getBean(BinderAwareChannelResolver.class);

    private final TargetCallBO targetCall;

    private final RuleTargetMq ruleTargetMq;

    public ProcessMqCall(TargetCallBO targetCall, TargetCallProcessBase ruleTargetMq) {
        this.targetCall = targetCall;
        this.ruleTargetMq = (RuleTargetMq) ruleTargetMq;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        boolean success = channelResolver.resolveDestination(ruleTargetMq.getQueue())
                .send(
                        MessageBuilder.withPayload(targetCall.getData()).build()
                );
        String mes;
        if (success) {
            mes = "发送mq成功";
        } else {
            mes = "发送mq失败";
        }
        return mes;
    }

}
