package com.sinosdx.service.event.bridge.service.impl.cell;

import com.sinosdx.service.event.bridge.dao.entity.RuleTargetSms;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessSmsCall extends AbstractProcessCall {

    private final RuleTargetSms ruleTargetSms;

    private final TargetCallBO targetCall;

    public ProcessSmsCall(TargetCallBO targetCall, TargetCallProcessBase ruleTargetSms) {
        this.targetCall = targetCall;
        this.ruleTargetSms = (RuleTargetSms) ruleTargetSms;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        return null;
    }
}
