package com.sinosdx.service.event.bridge.service.impl.transform;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONPath;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.enums.ConvertTypeEnum;
import com.sinosdx.service.event.bridge.service.IEventTransformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author pengjiahu
 * @date 2020-11-29 10:35
 * @description
 */
@Slf4j
@Service
public class EventTransformServiceImpl implements IEventTransformService {

    private static final String SEPARATE = "||";
    private static final String SEPARATE_CONSTANT = "->";

    @Override
    public Object eventTransform(Object data, RuleTarget ruleTarget) {
        log.debug("6.事件进行转换处理，data：{}", data);
        if(ObjectUtil.isNull(data)){
            return data;
        }
        switch (ConvertTypeEnum.formType(ruleTarget.getConvertType())) {
            case SECTION:
                data = section(data, ruleTarget);
                break;
            case CONSTANT:
                data = constant(data, ruleTarget);
                break;
            case TEMPLATE:
                data = template(data, ruleTarget);
                break;
            default:
        }
        return data;
    }

    private Object constant(Object data, RuleTarget ruleTarget) {
        if (StringUtils.isNotBlank(ruleTarget.getConstantValue())) {
            data = ruleTarget.getConstantValue();
        }
        return data;
    }

    /**
     * 部分事件
     * 只支持定义一个变量
     *
     * @param data
     * @param ruleTarget
     */
    private Object section(Object data, RuleTarget ruleTarget) {
        return dataParsing(String.valueOf(data), ruleTarget.getSectionExpression());
    }

    /**
     * 模板
     * 换模板中的spEL表达式
     * 可输出为：String、JSON
     *
     * @param data
     * @param ruleTarget {
     *                   "sheet_num": "100",
     *                   "result": [
     *                   {
     *                   "key?": "result",
     *                   "Id": "{id}",
     *                   "Name": "{name}"
     *                   }
     *                   ],
     *                   "desc": "所有的{desc}相加",
     *                   "Nodesc": "所有的{nodesc}相加",
     *                   "text": "{desc} \n {text}"
     *                   }
     *                   {id}->$..id||{name}->$..name||{desc}->$..desc||{nodesc}->$..nodesc||{text}->$..text
     */
    private Object template(Object data, RuleTarget ruleTarget) {
        String constantExpression = ruleTarget.getConstantExpression();
        String template = ruleTarget.getTemplate();
        if (StringUtils.isBlank(constantExpression) || StringUtils.isBlank(template)) {
            log.error("模板类型，constantExpression或template为空");
            return data;
        }
        return dataTemplateParsing(String.valueOf(data), constantExpression, template);
    }

    @Override
    public Object dataParsing(String data, String expression) {
        try {
            return JsonPath.parse(data).read(expression);
        } catch (PathNotFoundException e) {
            log.error("表达式：{}，数据：{}，未匹配到数据", data, expression);
            return "表达式：" + expression + "，未匹配到数据";
        }
    }

    /**
     * 模板转换（只支持一级）
     * @param data
     * @param constantExpression
     * @param template
     * @return
     */
    @Override
    public Object dataTemplateParsing(String data, String constantExpression, String template) {
        try {
            String[] strArr = StringUtils.split(constantExpression, SEPARATE);
            if (ArrayUtil.isNotEmpty(strArr)) {
                for (String str : strArr) {
                    String[] item = StringUtils.split(str, SEPARATE_CONSTANT);
                    Object eval = JSONPath.read(String.valueOf(data), item[1]);
                    template = StrUtil.replaceIgnoreCase(template, item[0], String.valueOf(eval));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }

}

