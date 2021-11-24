package com.sinosdx.service.event.bridge.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.enums.ConvertTypeEnum;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author pengjiahu
 * @date 2021-01-12 22:27
 * @description
 */
@UtilityClass
public class RuleUtil {

    public void checkRuleParam(Object object) {
        if (ObjectUtil.isNull(object)) {
            return;
        }
        String error = "";
        RuleTarget ruleTarget = new RuleTarget();
        BeanUtil.copyProperties(object, ruleTarget);
        switch (ConvertTypeEnum.formType(ruleTarget.getConvertType())) {
            case SECTION:
                if (StringUtils.isBlank(ruleTarget.getSectionExpression())) {
                    error = "部分事件类型，事件解析表达式不能为空";
                }
                break;
            case CONSTANT:
                if (StringUtils.isBlank(ruleTarget.getConstantValue())) {
                    error = "常量类型，常量值不能为空";
                }
                break;
            case TEMPLATE:
                if (StringUtils.isBlank(ruleTarget.getConstantExpression()) || StringUtils
                        .isBlank(ruleTarget.getTemplate())) {
                    error = "模板类型，变量表达式和模板值不能为空";
                }
                break;
            default:
        }
        if (StringUtils.isNotBlank(error)) {
            AssertsUtil.fail(error);
        }
    }
}
