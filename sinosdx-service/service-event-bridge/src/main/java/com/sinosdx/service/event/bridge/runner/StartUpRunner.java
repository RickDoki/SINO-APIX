package com.sinosdx.service.event.bridge.runner;

import com.sinosdx.service.event.bridge.service.IEventDeliveryService;
import com.sinosdx.service.event.bridge.service.IEventProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动后运行
 *
 * @author pengjiahu
 * @date 2020-09-09 10:44
 * @description
 */
@Slf4j
@Component
public class StartUpRunner implements ApplicationRunner {

    @Autowired
    private IEventProcessService eventProcessService;

    @Autowired
    private IEventDeliveryService eventDeliveryService;

    @Override
    public void run(ApplicationArguments args) {
        eventProcessService.processEventQueueRun();
        eventDeliveryService.saveEventDeliveryRun();
    }

}
