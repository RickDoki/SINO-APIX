package com.sinosdx.service.event.bridge.controller;

import com.alibaba.fastjson.JSON;
import com.sinosdx.client.event.CloudEvent;
import com.sinosdx.client.event.service.IEventBusService;
import com.sinosdx.client.event.service.IEventCompletion;
import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.constants.CommonConstant;
import com.sinosdx.service.event.bridge.controller.dto.EventDTO;
import com.sinosdx.service.event.bridge.service.IEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author pengjiahu
 * @date 2020-11-20 17:30
 * @description
 */
@RestController
@ResponseResult
@Validated
@RequestMapping("events")
@Api(tags = "事件")
@Slf4j
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private IEventBusService eventBusService;

    @Autowired
    private IEventCompletion sendEvent;

    @Autowired
    private StreamBridge streamBridge;

    @ApiOperation(value = "查询事件详情")
    @GetMapping("{id}")
    public Object getEventDetail(@PathVariable("id") Integer id) {
        EventDTO eventDTO = eventService.getEventDetail(id);
        if (eventDTO == null) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return eventDTO;
    }

    @ApiOperation(value = "新增事件")
    @PostMapping
    public Object saveEvent(@Valid @RequestBody CloudEvent cloudEvent) {
        log.debug("接收到API事件消息：{}", JSON.toJSONString(cloudEvent));
        return eventService.saveEvent(cloudEvent);
    }

    @ApiOperation(value = "新增事件(通过调用api发送)")
    @PostMapping("api/api")
    public CloudEvent saveEventByApi() {
        return CloudEvent.builder().busName("platform").source("eventBus")
                .type("event:test:api").data("test api send").build(eventBusService);
    }

    @ApiOperation(value = "新增事件(通过event sdk mq发送)")
    @PostMapping("api/mq")
    public void saveEventByApiEvent() {
        String data = "{\"name\":\"EventBridge\",\"scope\":100}";
        CloudEvent.builder().busName("default").data(data).source("test source").type("test type").build(sendEvent);
    }

    @ApiOperation(value = "新增事件(发送到动态的topic上)")
    @PostMapping("dynamics/event")
    public void dynamicDest(
            @RequestParam(value = "dest", defaultValue = CommonConstant.DYNAMIC_EVENT) String dest,
            @RequestParam(value = "body", defaultValue = "test dynamics dest") String body) {
        CloudEvent cloudEvent = CloudEvent.builder().data(body).source("test source")
                .type("test type").build();
        streamBridge.send(dest, cloudEvent);
    }

    @PostMapping("test/params")
    public void test(@RequestParam("data") String data) {
        log.info("test/params 接收到数据:{}", data);
    }

    @PostMapping("test/body")
    public void test(@RequestBody Object data) {
        log.info("test/body 接收到数据:{}", data);
    }

    @PostMapping("test/callback")
    public void callback(@RequestBody Object data) {
        log.info("test/callback 接收到数据:{}", data);
    }

}
