package com.sinosdx.service.event.bridge.service.impl.process;

import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import com.sinosdx.service.event.bridge.dao.entity.Event;
import com.sinosdx.service.event.bridge.service.IEventProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.collection.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author pengjiahu
 * @date 2020-09-23 21:27
 * @description
 */
@Slf4j
@Service
public class EventQueueProcessServiceImpl implements IEventProcessService {

    @Autowired
    private EventPreProcess eventPreProcess;

    private final BlockingQueue<Event> eventLinkedBlockingQueue = new LinkedBlockingQueue<>();

    @Override
    public void offerQueue(Event event) {
        offerQueue(Collections.singletonList(event));
    }

    @Override
    public void offerQueue(List<Event> eventList) {
        try {
            eventList.forEach(event -> {
                boolean result = eventLinkedBlockingQueue.offer(event);
                if (!result) {
                    log.error("事件处理，数据写入队列失败");
                }
            });
        } catch (Exception e) {
            log.error("事件处理，数据写入队列发生错误！", e);
        }
    }

    @Async
    @Override
    public void processEventQueueRun() {
        log.info("启动异步线程处理事件出队");
        // 缓冲队列
        List<Event> bufferedEventList = Lists.newArrayList();
        while (true) {
            try {
                bufferedEventList.add(eventLinkedBlockingQueue.take());
                eventLinkedBlockingQueue.drainTo(bufferedEventList);
                if (ListUtil.isNotEmpty(bufferedEventList)) {
                    bufferedEventList.parallelStream()
                            .forEach(event -> eventPreProcess.preProcess(event));
                }
            } catch (Exception e) {
                log.error("事件处理队列发生错误！", e);
                // 防止缓冲队列填充数据出现异常时不断刷屏
                ThreadUtil.sleep(2000);
            } finally {
                if (ListUtil.isNotEmpty(bufferedEventList)) {
                    try {
                        bufferedEventList.clear();
                    } catch (Exception e) {
                        log.error("事件处理队列finally发生错误！", e);
                    }
                }
            }
        }
    }
}
