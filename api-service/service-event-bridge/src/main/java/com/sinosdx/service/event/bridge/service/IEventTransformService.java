package com.sinosdx.service.event.bridge.service;

import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;

/**
 * 事件转换
 *
 * @author pengjiahu
 * @date 2020-11-29 10:34
 * @description
 */
public interface IEventTransformService {

    /**
     * 事件转换
     *
     * @param object
     * @param ruleTarget
     * @return
     */
    Object eventTransform(Object object, RuleTarget ruleTarget);

    /**
     * 数据表达式匹配
     *
     * @param data
     * @param expression
     * @return
     */
    Object dataParsing(String data, String expression);

    /**
     * 模板替换
     *
     * @param data
     * @param constantExpression
     * @param template
     * @return
     */
    Object dataTemplateParsing(String data, String constantExpression, String template);
}
