package com.sinosdx.service.event.bridge.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;
import com.sinosdx.service.event.bridge.dao.entity.RuleTarget;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import com.sinosdx.service.event.bridge.service.ITargetCallBackService;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.impl.process.EventPreProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.sinosdx.service.event.bridge.constants.CommonConstant.DELIVERY_STATUS_FAIL;

/**
 * @author pengjiahu
 * @date 2020-12-26 01:33
 * @description
 */
@Slf4j
@Service
public class TargetCallBackServiceImpl implements ITargetCallBackService {

    @Autowired
    private EventPreProcess eventPreProcess;

    @Autowired
    private IRuleTargetService ruleTargetService;

    @Async
    @Override
    public void callBack(EventDelivery eventDelivery) {
        //判断是否需要回调、目标执行是否成功
        if (eventDelivery.getCallbackRuleTargetId() == null || eventDelivery.getDeliveryStatus()
                .equals(DELIVERY_STATUS_FAIL)) {
            return;
        }
        log.info("9.事件目标[{}]，进行回调目标[{}],目标结果为：{}", eventDelivery.getRuleTargetId(),
                eventDelivery.getCallbackRuleTargetId(), eventDelivery.getResponse());
        //事件目标调用
        TargetCallBO targetCallBO = new TargetCallBO();
        BeanUtil.copyProperties(eventDelivery, targetCallBO);
        RuleTarget ruleTarget = ruleTargetService
                .getRuleTargetById(targetCallBO.getCallbackRuleTargetId());
        if (ruleTarget == null) {
            log.error("事件目标[{}]，进行回调目标[{}]发生错误，未找到对应目标", eventDelivery.getRuleTargetId(),
                    eventDelivery.getCallbackRuleTargetId());
            return;
        }
        targetCallBO.setRuleTargetId(targetCallBO.getCallbackRuleTargetId());
        targetCallBO.setTargetType(ruleTarget.getTargetType());
        targetCallBO.setData(eventDelivery.getResponse());
        //置空处理，以防数据污染
        targetCallBO.setRuleId(null);
        targetCallBO.setCallbackRuleTargetId(null);
        targetCallBO.setTarget(null);
        eventPreProcess.process(targetCallBO, ruleTarget);
    }
}
