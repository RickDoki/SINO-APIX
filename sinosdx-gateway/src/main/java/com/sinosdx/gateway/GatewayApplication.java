package com.sinosdx.gateway;

import com.sinosdx.gateway.configuration.ApplicationListenerStarted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author pengjiahu
 * @date 2020/8/4 15:41
 * @description
 */
@Slf4j
@EnableAsync
@SpringBootApplication
@ComponentScan(value = {"com.sinosdx.gateway", "com.saic.csp2.middle.common.gateway"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
        springApplication.addListeners(new ApplicationListenerStarted());
        springApplication.run(args);
    }
}

