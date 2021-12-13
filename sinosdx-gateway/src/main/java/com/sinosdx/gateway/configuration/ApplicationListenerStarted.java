package com.sinosdx.gateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author pengjiahu
 * @date 2021-06-08 00:26
 * @description
 */
@Slf4j
@Component
public class ApplicationListenerStarted implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        //初始化数据
        log.info("application listener started.");
    }
}
