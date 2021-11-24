package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.service.event.bridge.dao.entity.*;
import lombok.*;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleTargetUpdateDTO extends BaseRuleTargetDTO {

    private RuleTargetHttp ruleTargetHttp;

    private RuleTargetMq ruleTargetMq;

    private RuleTargetEmail ruleTargetEmail;

    private RuleTargetSms ruleTargetSms;

    private RuleTargetDingTalkNotice ruleTargetDingTalkNotice;

    private RuleTargetWorkWeixinNotice ruleTargetWorkWeixinNotice;

    private RuleTargetInnerApi ruleTargetInnerApi;

    private RuleTargetRedis ruleTargetRedis;

    private CallbackRuleTargetUpdateDTO callbackTarget;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class CallbackRuleTargetUpdateDTO extends BaseRuleTargetDTO {

        private RuleTargetHttp ruleTargetHttp;

        private RuleTargetMq ruleTargetMq;

        private RuleTargetEmail ruleTargetEmail;

        private RuleTargetSms ruleTargetSms;

        private RuleTargetDingTalkNotice ruleTargetDingTalkNotice;

        private RuleTargetWorkWeixinNotice ruleTargetWorkWeixinNotice;

        private RuleTargetInnerApi ruleTargetInnerApi;

        private RuleTargetRedis ruleTargetRedis;
    }
}
