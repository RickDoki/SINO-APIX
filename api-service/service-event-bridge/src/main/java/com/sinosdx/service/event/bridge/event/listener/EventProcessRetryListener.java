package com.sinosdx.service.event.bridge.event.listener;


import cn.hutool.core.bean.BeanUtil;
import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.sinosdx.common.base.exceptions.BaseException;
import com.sinosdx.service.event.bridge.dao.entity.EventDelivery;
import com.sinosdx.service.event.bridge.service.IEventDeliveryService;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.sinosdx.service.event.bridge.constants.CommonConstant.DELIVERY_STATUS_FAIL;
import static com.sinosdx.service.event.bridge.constants.CommonConstant.DELIVERY_STATUS_SUCCESS;


/**
 * @author pengjiahu
 * @date 2020-12-02 15:58
 * @description
 */
@Slf4j
public class EventProcessRetryListener implements RetryListener {

    private IEventDeliveryService eventDeliveryService;

    public EventProcessRetryListener(IEventDeliveryService eventDeliveryService) {
        this.eventDeliveryService = eventDeliveryService;
    }

    @Override
    public <T> void onRetry(Attempt<T> attempt) {
        //第几次重试,(注意:第一次重试其实是第一次调用)
        Long attemptNum = attempt.getAttemptNumber();
        //距离第一次重试的延迟
        Long delaySinceFirstAttempt = attempt.getDelaySinceFirstAttempt();
        // 重试结果: 是异常终止, 还是正常返回
        boolean hasException = attempt.hasException();
        TargetCallBO targetCallBO;
        String response;
        int deliveryStatus = DELIVERY_STATUS_SUCCESS;
        if (hasException) {
            BaseException exception = (BaseException) attempt.getExceptionCause();
            targetCallBO = (TargetCallBO) exception.getData();
            response = exception.getMessage();
            deliveryStatus = DELIVERY_STATUS_FAIL;
        } else {
            targetCallBO = (TargetCallBO) attempt.getResult();
            response = targetCallBO.getResponse();
        }
        //投递日志放到队列中
        EventDelivery eventDelivery = new EventDelivery();
        BeanUtil.copyProperties(targetCallBO, eventDelivery);
        eventDelivery.setDeliveryStatus(deliveryStatus);
        eventDelivery.setDescription(
                buildEventDeliveryDesc(attemptNum, delaySinceFirstAttempt, hasException,
                        targetCallBO.getTimeConsumer(), response));
        eventDelivery.setProcessCompleteDate(LocalDateTime.now());
        eventDelivery.setResponse(response);
        eventDeliveryService.offerQueue(eventDelivery);
    }

    /**
     * 构建调用详情
     *
     * @param num
     * @param delay
     * @param hasException
     * @param timeConsumer
     * @param response
     * @return
     */
    public String buildEventDeliveryDesc(Long num, Long delay, boolean hasException,
            Long timeConsumer, String response) {
        long minutes = TimeUnit.MILLISECONDS.toSeconds(delay);
        String localDateTime = LocalDateTime.now().plusMinutes(minutes)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return localDateTime + "|"
                + (hasException ? "失败" : "成功") + "|" + "第" + num + "次投递,"
                + "投递耗时" + (timeConsumer == null ? 0 : timeConsumer) + "毫秒," + "投递响应为：" + response;
    }

}
