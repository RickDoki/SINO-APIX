package com.sinosdx.service.event.bridge.controller.dto;

import com.sinosdx.common.base.annotation.ValueIn;
import com.sinosdx.service.event.bridge.dao.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-29 14:12
 * @description
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleSaveDTO {

    @NotNull(message = "总线id不能为空")
    @ApiModelProperty("总线id")
    private Integer busId;

    @Length(max = 128)
    @NotBlank(message = "规则名称不能为空")
    @ApiModelProperty("规则名称")
    private String name;

    @Length(max = 256)
    @ApiModelProperty("规则描述")
    private String description;

    @Valid
    @ApiModelProperty("规则模式")
    private RuleModeInfo ruleModeInfo;

    @Valid
    @ApiModelProperty("事件目标")
    private List<RuleTargetInfo> ruleTargetInfoList;

    @Getter
    @Setter
    public static class RuleModeInfo {

        @Length(max = 128)
        @NotBlank(message = "事件源不能为空")
        @ApiModelProperty("事件源")
        private String source;

        @Length(max = 64)
        @NotBlank(message = "事件类型不能为空")
        @ApiModelProperty("事件类型")
        private String type;

        @ApiModelProperty("jsonPath过滤规则表达式")
        private String jsonPathExpression;

    }

    @Getter
    @Setter
    public static class RuleTargetInfo extends BaseTargetInfo {

        @ApiModelProperty("规则id")
        private Integer ruleId;

        private CallbackRuleTargetInfo callbackRuleTargetInfo;
    }

    public static class CallbackRuleTargetInfo extends BaseTargetInfo {

    }

    @Getter
    @Setter
    public static class BaseTargetInfo {

        @NotNull(message = "目标类型不能为空")
        @ValueIn(values = {"1", "2", "3", "4", "5", "6", "7",
                "8"}, message = "取值范围必须是 1：MQ  2：Http  3：短信  4：邮件")
        @ApiModelProperty("目标类型（1：MQ  2：Http  3：短信  4：邮件）")
        private Integer targetType;

        @NotNull(message = "转换类型不能为空")
        @ValueIn(values = {"1", "2", "3", "4"}, message = "取值范围必须是 1：完整事件  2：部分事件  3：常量  4：模板")
        @ApiModelProperty("转换类型（1：完整事件  2：部分事件  3：常量  4：模板）")
        private Integer convertType;

        @Length(max = 2048)
        @ApiModelProperty("部分事件解析表达式")
        private String sectionExpression;

        @Length(max = 2048)
        @ApiModelProperty("常量值")
        private String constantValue;

        @Length(max = 2048)
        @ApiModelProperty("模板-变量或表达式")
        private String constantExpression;

        @Length(max = 2048)
        @ApiModelProperty("模板")
        private String template;

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
