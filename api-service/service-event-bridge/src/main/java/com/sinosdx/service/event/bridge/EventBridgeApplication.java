package com.sinosdx.service.event.bridge;

import com.sinosdx.common.usf.configuration.ValidatorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pengjiahu
 * @date 2020/11/14 13:33
 */
@SpringBootApplication
@ComponentScan(value = {"com.sinosdx.service.event.bridge"}, basePackageClasses = {
        ValidatorConfiguration.class})
public class EventBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventBridgeApplication.class, args);
    }
}
