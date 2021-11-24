package com.sinosdx.service.event.bridge.service.impl.cell;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetEmail;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import com.sinosdx.support.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessEmailCall extends AbstractProcessCall {

    private final RuleTargetEmail emailTarget;

    private final TargetCallBO targetCall;

    private final EmailService emailService = SpringContextHolder.getBean(EmailService.class);

    public ProcessEmailCall(TargetCallBO targetCall, TargetCallProcessBase emailTarget) {
        this.targetCall = targetCall;
        this.emailTarget = (RuleTargetEmail) emailTarget;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        String content = StringEscapeUtils.unescapeJava(String.valueOf(targetCall.getData()));
        log.debug("99.事件进行Email目标调用,content:{}", content);
        emailService.send(emailTarget.getSubject(), content, emailTarget.getToAddress());
        return null;
    }
}
