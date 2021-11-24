package com.sinosdx.service.event.bridge.service.impl.cell;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetRedis;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import com.sinosdx.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessRedisCall extends AbstractProcessCall {

    private final RedisService redisService = SpringContextHolder.getBean(RedisService.class);

    private final RuleTargetRedis ruleTargetRedis;

    private final TargetCallBO targetCall;

    public ProcessRedisCall(TargetCallBO targetCall, TargetCallProcessBase ruleTargetRedis) {
        this.targetCall = targetCall;
        this.ruleTargetRedis = (RuleTargetRedis) ruleTargetRedis;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        redisService.set(ruleTargetRedis.getKey(), targetCall.getData(),
                ruleTargetRedis.getExpiredTime());
        return null;
    }
}
