package com.sinosdx.client.event.service;


import com.sinosdx.client.event.CloudEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author pengjiahu
 * @date 2020/8/6 10:51
 * @description
 */
@FeignClient(value = "${sinosdx.api.middle.bus:service-event-bridge}", path = "events")
public interface IEventBusService {

    /**
     * 发送事件
     *
     * @param cloudEvent
     * @return
     */
    @PostMapping
    Object saveEvent(@RequestBody CloudEvent cloudEvent);
}
