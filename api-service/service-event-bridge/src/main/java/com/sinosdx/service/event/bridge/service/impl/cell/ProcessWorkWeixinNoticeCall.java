package com.sinosdx.service.event.bridge.service.impl.cell;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetWorkWeixinNotice;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import com.sinosdx.starter.workweixin.robot.entity.TextMessage;
import com.sinosdx.starter.workweixin.robot.exception.MessageException;
import com.sinosdx.starter.workweixin.robot.message.Sender;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessWorkWeixinNoticeCall extends AbstractProcessCall {

    private final Sender sender = SpringContextHolder.getBean("defaultSender");

    private final RuleTargetWorkWeixinNotice ruleTargetWorkWeixinNotice;

    private final TargetCallBO targetCall;

    public ProcessWorkWeixinNoticeCall(TargetCallBO targetCall,
            TargetCallProcessBase ruleTargetWorkWeixinNotice) {
        this.targetCall = targetCall;
        this.ruleTargetWorkWeixinNotice = (RuleTargetWorkWeixinNotice) ruleTargetWorkWeixinNotice;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        TextMessage textMsg = new TextMessage();
        String content = JSON.toJSONString(targetCall.getData());
        textMsg.setContent(StrUtil.maxLength(content, 2045));
        try {
            sender.send(textMsg);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessageException e) {
            e.printStackTrace();
            log.error("企微机器人消息发送失败!");
        }
        return "";
    }
}
