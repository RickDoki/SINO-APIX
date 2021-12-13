package com.sinosdx.service.event.bridge.service.impl.cell;

import com.alibaba.fastjson.JSON;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetDingTalkNotice;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import com.sinosdx.starter.dingtalk.robot.sender.DingTalkMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessDingTalkNoticeCall extends AbstractProcessCall {

    private final RuleTargetDingTalkNotice ruleTargetDingTalkNotice;

    private final TargetCallBO targetCall;

    public ProcessDingTalkNoticeCall(TargetCallBO targetCall,
            TargetCallProcessBase ruleTargetDingTalkNotice) {
        this.targetCall = targetCall;
        this.ruleTargetDingTalkNotice = (RuleTargetDingTalkNotice) ruleTargetDingTalkNotice;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        int result = DingTalkMessage.builder().text()
                .content(JSON.toJSONString(targetCall.getData()))
                .and().send();
        return result >= 1 ? "成功" : "失败";
    }
}
