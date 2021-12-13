package com.sinosdx.service.event.bridge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.service.event.bridge.assembler.RuleTargetConverter;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.*;
import com.sinosdx.service.event.bridge.dao.mapper.*;
import com.sinosdx.service.event.bridge.enums.TargetTypeEnum;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:56
 * @description
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "rule_target")
public class RuleTargetServiceImpl extends
        ServiceImpl<RuleTargetMapper, RuleTarget> implements IRuleTargetService {

    public static final String LIMIT_1 = "LIMIT 1";

    @Autowired
    private RuleTargetHttpMapper ruleTargetHttpMapper;

    @Autowired
    private RuleTargetMqMapper ruleTargetMqMapper;

    @Autowired
    private RuleTargetEmailMapper ruleTargetEmailMapper;

    @Autowired
    private RuleTargetSmsMapper ruleTargetSmsMapper;

    @Autowired
    private RuleTargetDingTalkNoticeMapper ruleTargetDingTalkNoticeMapper;

    @Autowired
    private RuleTargetWorkWeixinNoticeMapper ruleTargetWorkWeixinNoticeMapper;

    @Autowired
    private RuleTargetInnerApiMapper ruleTargetInnerApiMapper;

    @Autowired
    private RuleTargetRedisMapper ruleTargetRedisMapper;

    @Autowired(required = false)
    private RuleTargetConverter ruleTargetConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean updateRuleTarget(RuleTargetUpdateDTO ruleTargetUpdateDTO) {
        if (this.getRuleTargetById(ruleTargetUpdateDTO.getId()) == null) {
            AssertsUtil.fail("该规则目标不存在");
        }
        RuleTarget ruleTarget = ruleTargetConverter.updateDTOTo(ruleTargetUpdateDTO);
        this.updateById(ruleTarget);
        switch (TargetTypeEnum.formType(ruleTarget.getTargetType())) {
            case MQ:
                ruleTargetMqMapper.update(ruleTargetUpdateDTO.getRuleTargetMq(),
                        new LambdaQueryWrapper<RuleTargetMq>()
                                .eq(RuleTargetMq::getRuleTargetId, ruleTargetUpdateDTO.getId()));
                break;
            case HTTP:
                ruleTargetHttpMapper.update(ruleTargetUpdateDTO.getRuleTargetHttp(),
                        new LambdaQueryWrapper<RuleTargetHttp>()
                                .eq(RuleTargetHttp::getRuleTargetId, ruleTargetUpdateDTO.getId()));
                break;
            case EMAIL:
                ruleTargetEmailMapper.update(ruleTargetUpdateDTO.getRuleTargetEmail(),
                        new LambdaQueryWrapper<RuleTargetEmail>()
                                .eq(RuleTargetEmail::getRuleTargetId, ruleTargetUpdateDTO.getId()));
                break;
            case SMS:
                ruleTargetSmsMapper.update(ruleTargetUpdateDTO.getRuleTargetSms(),
                        new LambdaQueryWrapper<RuleTargetSms>()
                                .eq(RuleTargetSms::getRuleTargetId, ruleTargetUpdateDTO.getId()));
                break;
            case DINGTALK_NOTICE:
                ruleTargetWorkWeixinNoticeMapper
                        .update(ruleTargetUpdateDTO.getRuleTargetWorkWeixinNotice(),
                                new LambdaQueryWrapper<RuleTargetWorkWeixinNotice>()
                                        .eq(RuleTargetWorkWeixinNotice::getRuleTargetId,
                                                ruleTargetUpdateDTO.getId()));
                break;
            case WORK_WEIXIN_NOTICE:
                ruleTargetDingTalkNoticeMapper
                        .update(ruleTargetUpdateDTO.getRuleTargetDingTalkNotice(),
                                new LambdaQueryWrapper<RuleTargetDingTalkNotice>()
                                        .eq(RuleTargetDingTalkNotice::getRuleTargetId,
                                                ruleTargetUpdateDTO.getId()));
                break;
            case INNER_API:
                ruleTargetInnerApiMapper.update(ruleTargetUpdateDTO.getRuleTargetInnerApi(),
                        new LambdaQueryWrapper<RuleTargetInnerApi>()
                                .eq(RuleTargetInnerApi::getRuleTargetId,
                                        ruleTargetUpdateDTO.getId()));
                break;
            case REDIS:
                ruleTargetRedisMapper.update(ruleTargetUpdateDTO.getRuleTargetRedis(),
                        new LambdaQueryWrapper<RuleTargetRedis>()
                                .eq(RuleTargetRedis::getRuleTargetId, ruleTargetUpdateDTO.getId()));
                break;
            default:
        }
        return true;
    }

    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public RuleTarget getRuleTargetById(Integer id) {
        return this.getById(id);
    }

    @Override
    @Cacheable(key = "#eventRuleTargetId", unless = "#result == null")
    public RuleTargetDTO getRuleTargetDetail(Integer eventRuleTargetId) {
        RuleTarget ruleTarget = this.getById(eventRuleTargetId);
        if (ruleTarget == null) {
            return null;
        }
        RuleTargetDTO targetDTO = ruleTargetConverter.toDTO(ruleTarget);
        targetDTO.setTargetInfo(getTargetInfo(targetDTO.getTargetType(), targetDTO.getId()));
        return targetDTO;
    }

    @Override
    @Cacheable(key = "#type+'-'+#targetId", unless = "#result == null")
    public Object getTargetInfo(Integer type, Integer targetId) {
        Object object = null;
        switch (TargetTypeEnum.formType(type)) {
            case MQ:
                object = this.getTargetMqByTargetId(targetId);
                break;
            case HTTP:
                object = this.getTargetHttpByTargetId(targetId);
                break;
            case EMAIL:
                object = this.getTargetEmailByTargetId(targetId);
                break;
            case SMS:
                object = this.getTargetSmsByTargetId(targetId);
                break;
            case DINGTALK_NOTICE:
                object = this.getTargetDingTalkNoticeByTargetId(targetId);
                break;
            case WORK_WEIXIN_NOTICE:
                object = this.getTargetWorkWeixinNoticeByTargetId(targetId);
                break;
            case INNER_API:
                object = this.getTargetInnerApiByTargetId(targetId);
                break;
            case REDIS:
                object = this.getTargetRedisByTargetId(targetId);
                break;
            default:
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchRuleTarget(List<RuleTarget> ruleTargetList) {
        return this.saveBatch(ruleTargetList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleTarget(RuleTarget ruleTarget) {
        return this.save(ruleTarget);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id", allEntries = true)
    public boolean removeRuleTarget(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Cacheable(key = "#eventRuleId", unless = "#result == null")
    public List<RuleTarget> getRuleTargetListByRuleId(Integer eventRuleId) {
        return new LambdaQueryChainWrapper<>(baseMapper).eq(RuleTarget::getRuleId, eventRuleId)
                .list();
    }

    @Override
    public RuleTargetMq getTargetMqByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetMqMapper)
                .eq(RuleTargetMq::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetHttp getTargetHttpByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetHttpMapper)
                .eq(RuleTargetHttp::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetEmail getTargetEmailByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetEmailMapper)
                .eq(RuleTargetEmail::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetSms getTargetSmsByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetSmsMapper)
                .eq(RuleTargetSms::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetDingTalkNotice getTargetDingTalkNoticeByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetDingTalkNoticeMapper)
                .eq(RuleTargetDingTalkNotice::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetWorkWeixinNotice getTargetWorkWeixinNoticeByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetWorkWeixinNoticeMapper)
                .eq(RuleTargetWorkWeixinNotice::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetInnerApi getTargetInnerApiByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetInnerApiMapper)
                .eq(RuleTargetInnerApi::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public RuleTargetRedis getTargetRedisByTargetId(Integer targetId) {
        return new LambdaQueryChainWrapper<>(ruleTargetRedisMapper)
                .eq(RuleTargetRedis::getRuleTargetId, targetId).last(LIMIT_1).one();
    }

    @Override
    public List<RuleTargetMq> getTargetMqListByTargetId(List<Integer> ruleTargetIdList) {
        return ruleTargetMqMapper.selectList(new LambdaQueryWrapper<RuleTargetMq>()
                .in(RuleTargetMq::getRuleTargetId, ruleTargetIdList));
    }

    @Override
    public List<RuleTargetHttp> getTargetHttpListByTargetId(List<Integer> ruleTargetIdList) {
        return ruleTargetHttpMapper.selectList(new LambdaQueryWrapper<RuleTargetHttp>()
                .in(RuleTargetHttp::getRuleTargetId, ruleTargetIdList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleTargetHttp(RuleTargetHttp ruleTargetHttp) {
        return ruleTargetHttpMapper.insert(ruleTargetHttp) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleTargetMq(RuleTargetMq ruleTargetMq) {
        return ruleTargetMqMapper.insert(ruleTargetMq) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleTargetDingTalk(RuleTargetDingTalkNotice dingTalkNotice) {
        return ruleTargetDingTalkNoticeMapper.insert(dingTalkNotice) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRuleTargetWorkWeixin(RuleTargetWorkWeixinNotice workWeixinNotice) {
        return ruleTargetWorkWeixinNoticeMapper.insert(workWeixinNotice) > 0;
    }

    @Override
    public List<RuleTarget> getRuleTargetListByRuleIds(List<Integer> ruleIds) {
        return new LambdaQueryChainWrapper<>(baseMapper).in(RuleTarget::getRuleId, ruleIds).list();
    }
}
