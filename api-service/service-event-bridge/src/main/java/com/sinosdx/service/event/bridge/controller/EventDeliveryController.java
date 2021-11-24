package com.sinosdx.service.event.bridge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.controller.dto.EventDeliveryDTO;
import com.sinosdx.service.event.bridge.controller.query.EventDeliveryQuery;
import com.sinosdx.service.event.bridge.service.IEventDeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjiahu
 * @date 2020-11-20 17:30
 * @description
 */
@RestController
@ResponseResult
@Validated
@RequestMapping("events/deliveries")
@Api(tags = "事件投递轨迹")
public class EventDeliveryController {

    @Autowired
    private IEventDeliveryService eventDeliveryService;

    @ApiOperation(value = "查询事件轨迹详情")
    @GetMapping("{eventId}")
    public Object getEventDeliveryDetail(@PathVariable("eventId") Integer eventId) {
        EventDeliveryDTO eventDeliveryDTO = eventDeliveryService.getEventDeliveryDetail(eventId);
        if (eventDeliveryDTO == null) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return eventDeliveryDTO;
    }

    @ApiOperation(value = "查询事件轨迹列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "name", required = true, value = "名称", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "description", required = true, value = "描述", paramType = "form", dataTypeClass = Long.class)
    })
    @GetMapping
    public IPage getEventDeliveryList(Page page, EventDeliveryQuery eventDeliveryQuery) {
        return eventDeliveryService.getEventDeliveryList(page, eventDeliveryQuery);
    }

    @ApiOperation(value = "查询失败的事件目标调用列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "name", required = true, value = "名称", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "description", required = true, value = "描述", paramType = "form", dataTypeClass = Long.class)
    })
    @GetMapping("fail")
    public IPage getEventDeliveryFailList(Page page) {
        return eventDeliveryService.getEventDeliveryFailList(page);
    }

}
