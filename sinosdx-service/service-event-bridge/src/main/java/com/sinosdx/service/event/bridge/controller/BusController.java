package com.sinosdx.service.event.bridge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.controller.dto.BusDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.BusQuery;
import com.sinosdx.service.event.bridge.service.IBusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("buses")
@Api(tags = "事件总线")
public class BusController {

    @Autowired
    private IBusService busService;

    @ApiOperation(value = "保存事件总线")
    @PostMapping
    public Object saveBus(@Valid @RequestBody BusSaveDTO busSaveDTO) {
        return busService.saveBus(busSaveDTO);
    }

    @ApiOperation(value = "修改事件总线")
    @PutMapping
    public Object updateBus(@Valid @RequestBody BusUpdateDTO busUpdateDTO) {
        return busService.updateBus(busUpdateDTO);
    }

    @ApiOperation(value = "查询事件总线详情")
    @GetMapping("{busId}")
    public Object getBusDetail(@PathVariable("busId") Integer busId) {
        BusDTO busDTO = busService.getBusDetail(busId);
        if (busDTO == null) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return busDTO;
    }

    @ApiOperation(value = "查询事件总线列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "name", required = true, value = "名称", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "description", required = true, value = "描述", paramType = "form", dataTypeClass = Long.class)
    })
    @GetMapping
    public IPage getBusList(Page page, BusQuery busQuery) {
        return busService.getBusList(page, busQuery);
    }

    @ApiOperation(value = "删除事件总线")
    @DeleteMapping("{busId}")
    public Object deleteBus(@PathVariable("busId") Integer busId) {
        boolean result = busService.deleteBus(busId);
        if (!result) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return null;
    }

}
