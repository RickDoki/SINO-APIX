package com.sinosdx.service.event.bridge.service.impl.filter;

import cn.hutool.core.util.ObjectUtil;
import com.jayway.jsonpath.JsonPath;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.dao.entity.RuleMode;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.service.IEventFilterService;
import com.sinosdx.service.event.bridge.service.IRuleModeService;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.collection.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pengjiahu
 * @date 2020-11-29 10:35
 * @description
 */
@Slf4j
@Service
public class EventFilterServiceImpl implements IEventFilterService {

    @Autowired
    private IRuleModeService ruleModeService;

    @Autowired
    private IRuleTargetService ruleTargetService;

    @Override
    public List<RuleTarget> matchEventMode(Event event) {
        log.debug("5.事件进行匹配规则,{}", event);
        //匹配规则模式，找到规则集合
        List<RuleMode> ruleModeList = ruleModeService
                .getRuleModeListBySourceAndType(event.getSource(), event.getType());
        if (ListUtil.isEmpty(ruleModeList)) {
            return Collections.emptyList();
        }
        //规则过滤处理
        List<Integer> ruleIdList = matchCustomParam(ruleModeList, event.getData());
        if (ListUtil.isEmpty(ruleIdList)) {
            return Collections.emptyList();
        }
        //查找事件规则集合
        return ruleTargetService.getRuleTargetListByRuleIds(ruleIdList);
    }

    /**
     * 进行规则过滤处理，获取待处理规则id集合
     * jsonPath过滤规则表达式,https://github.com/json-path/JsonPath
     * 验证测试地址：http://jsonpath.herokuapp.com/
     *
     * @param ruleModeList
     * @param data
     * @return
     */
    public List<Integer> matchCustomParam(List<RuleMode> ruleModeList, Object data) {
        return ruleModeList.parallelStream().filter(item -> {
            String jsonPathExpression = item.getJsonPathExpression();
            if (StringUtils.isNotBlank(jsonPathExpression)) {
                try {
                    log.debug("matchCustomParam before:{}", data);
                    String dataStr = ((String) data).replace("\"{", "{").replace("}\"", "}");
                    log.debug("matchCustomParam after:{}", dataStr);
                    Object value = JsonPath.parse(dataStr).read(item.getJsonPathExpression());
                    return ObjectUtil.isNotEmpty(value);
                } catch (Exception e) {
                    log.error("规则Id[{}]，匹配规则[{}]，匹配数据错误", item.getRuleId(),
                            item.getJsonPathExpression(), e);
                    return false;
                }
            }
            return true;
        }).map(RuleMode::getRuleId).collect(Collectors.toList());
    }
}
