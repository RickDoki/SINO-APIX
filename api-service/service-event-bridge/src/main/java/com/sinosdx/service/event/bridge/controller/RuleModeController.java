package com.sinosdx.service.event.bridge.controller;

import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleModeUpdateDTO;
import com.sinosdx.service.event.bridge.service.IRuleModeService;
import io.swagger.annotations.Api;
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
@RequestMapping("buses/rules/modes")
@Api(tags = "规则模式")
public class RuleModeController {

    @Autowired
    private IRuleModeService ruleModeService;

    @ApiOperation(value = "修改规则模式")
    @PutMapping
    public Object updateRuleMode(@Valid @RequestBody RuleModeUpdateDTO ruleModeUpdateDTO) {
        return ruleModeService.updateRuleMode(ruleModeUpdateDTO);
    }

    @ApiOperation(value = "查询规则模式")
    @GetMapping("{ruleModeId}")
    public Object getRuleModeDetail(@PathVariable("ruleModeId") Integer ruleModeId) {
        RuleModeDTO ruleModeDTO = ruleModeService.getRuleModeDetail(ruleModeId);
        if (ruleModeDTO == null) {
             return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return ruleModeDTO;
    }

}
