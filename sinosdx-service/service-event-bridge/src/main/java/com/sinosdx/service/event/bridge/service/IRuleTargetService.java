package com.sinosdx.service.event.bridge.service;

import com.sinosdx.service.event.bridge.controller.dto.RuleTargetDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetUpdateDTO;
import com.sinosdx.service.event.bridge.dao.entity.*;

import java.util.List;

/**
 * 事件目标
 *
 * @author pengjiahu
 * @date 2020-11-29 10:34
 * @description
 */
public interface IRuleTargetService {

    /**
     * 修改事件目标
     *
     * @param ruleTargetUpdateDTO
     * @return
     */
    boolean updateRuleTarget(RuleTargetUpdateDTO ruleTargetUpdateDTO);

    /**
     * 查询事件目标
     *
     * @param ruleTargetId
     * @return
     */
    RuleTargetDTO getRuleTargetDetail(Integer ruleTargetId);

    /**
     * 根据目标id查询详情
     *
     * @param id
     * @return
     */
    RuleTarget getRuleTargetById(Integer id);

    /**
     * 删除目标
     *
     * @param id
     * @return
     */
    boolean removeRuleTarget(Integer id);

    /**
     * 批量保存事件目标
     *
     * @param ruleTargetList
     * @return
     */
    boolean saveBatchRuleTarget(List<RuleTarget> ruleTargetList);

    /**
     * 保存事件目标
     *
     * @param ruleTarget
     * @return
     */
    boolean saveRuleTarget(RuleTarget ruleTarget);

    /**
     * 根据规则id查询事件规则目标
     *
     * @param eventRuleId
     * @return
     */
    List<RuleTarget> getRuleTargetListByRuleId(Integer eventRuleId);

    /**
     * 查询MQ目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetMq getTargetMqByTargetId(Integer targetId);

    /**
     * 查询HTTP目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetHttp getTargetHttpByTargetId(Integer targetId);

    /**
     * 查询Email目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetEmail getTargetEmailByTargetId(Integer targetId);

    /**
     * 查询Sms目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetSms getTargetSmsByTargetId(Integer targetId);

    /**
     * 查询DingTalkNotice目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetDingTalkNotice getTargetDingTalkNoticeByTargetId(Integer targetId);

    /**
     * 查询WorkWeixinNotice目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetWorkWeixinNotice getTargetWorkWeixinNoticeByTargetId(Integer targetId);

    /**
     * 查询InnerApi目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetInnerApi getTargetInnerApiByTargetId(Integer targetId);

    /**
     * 查询Redis目标详情
     *
     * @param targetId
     * @return
     */
    RuleTargetRedis getTargetRedisByTargetId(Integer targetId);

    /**
     * 根据事件目标id集合查询MQ目标列表
     *
     * @param ruleTargetIdList
     * @return
     */
    List<RuleTargetMq> getTargetMqListByTargetId(List<Integer> ruleTargetIdList);

    /**
     * 根据事件目标id集合查询HTTP目标列表
     *
     * @param ruleTargetIdList
     * @return
     */
    List<RuleTargetHttp> getTargetHttpListByTargetId(List<Integer> ruleTargetIdList);

    /**
     * 保存HTTP事件目标
     *
     * @param ruleTargetHttp
     * @return
     */
    boolean saveRuleTargetHttp(RuleTargetHttp ruleTargetHttp);

    /**
     * 保存MQ事件目标
     *
     * @param ruleTargetMq
     * @return
     */
    boolean saveRuleTargetMq(RuleTargetMq ruleTargetMq);

    /**
     * 保存Dingtalk事件目标
     *
     * @param dingTalkNotice
     * @return
     */
    boolean saveRuleTargetDingTalk(RuleTargetDingTalkNotice dingTalkNotice);

    /**
     * 保存WorkWeixin事件目标
     *
     * @param workWeixinNotice
     * @return
     */
    boolean saveRuleTargetWorkWeixin(RuleTargetWorkWeixinNotice workWeixinNotice);

    /**
     * 查询事件目标详情
     *
     * @param type
     * @param targetId
     * @return
     */
    Object getTargetInfo(Integer type, Integer targetId);

    /**
     * 根据规则id集合查询规则目标集合
     *
     * @param ruleIds
     * @return
     */
    List<RuleTarget> getRuleTargetListByRuleIds(List<Integer> ruleIds);
}
