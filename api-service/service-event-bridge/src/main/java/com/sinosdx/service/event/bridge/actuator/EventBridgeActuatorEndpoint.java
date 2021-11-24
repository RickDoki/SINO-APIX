package com.sinosdx.service.event.bridge.actuator;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

import java.util.Map;

/**
 * @author pengjiahu
 * @date 2021-01-05 13:46
 * @description
 */
@WebEndpoint(id = "event-bridge")
public class EventBridgeActuatorEndpoint {

    /**
     * 查看各事件处理器实例
     *
     * @return
     */
    @ReadOperation
    public Map<String, Object> EventProcessActuator() {
        //TODO
        return null;
    }

}
