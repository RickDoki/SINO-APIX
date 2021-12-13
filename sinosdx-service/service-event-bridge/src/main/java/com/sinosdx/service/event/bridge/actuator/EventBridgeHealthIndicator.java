package com.sinosdx.service.event.bridge.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author pengjiahu
 * @date 2021-01-05 13:59
 * @description
 */
@Component("event-bridge-health")
public class EventBridgeHealthIndicator implements HealthIndicator {

    /**
     * 健康检查
     *
     * @return
     */
    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        if (true) {
            //TODO
            return builder.up().build();
        } else {
            return builder.down().build();
        }
    }
}
