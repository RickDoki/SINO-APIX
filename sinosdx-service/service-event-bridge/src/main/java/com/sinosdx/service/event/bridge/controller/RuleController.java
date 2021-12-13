package com.sinosdx.service.event.bridge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.config.EventBridgeProperties;
import com.sinosdx.service.event.bridge.controller.dto.RuleDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.RuleQuery;
import com.sinosdx.service.event.bridge.service.IRuleService;
import com.sinosdx.service.event.bridge.utils.RuleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.utils.collection.ListUtil;

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
@Api(tags = "总线规则")
public class RuleController {

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private EventBridgeProperties eventBridgeProperties;

    @ApiOperation(value = "保存总线规则")
    @PostMapping("rules")
    public Object saveRule(@Valid @RequestBody RuleSaveDTO ruleSaveDTO) {
        //参数校验
        if (ListUtil.isEmpty(ruleSaveDTO.getRuleTargetInfoList())) {
            return R.fail("事件目标不能为空");
        }
        if (ruleSaveDTO.getRuleTargetInfoList().size() > eventBridgeProperties
                .getEventTargetNumMax()) {
            return R.fail("事件目标可添加数，不能大于" + eventBridgeProperties.getEventTargetNumMax() + "个");
        }
        for (RuleSaveDTO.RuleTargetInfo item : ruleSaveDTO.getRuleTargetInfoList()) {
            RuleUtil.checkRuleParam(item);
        }
        return ruleService.saveRule(ruleSaveDTO);
    }

    @ApiOperation(value = "修改总线规则")
    @PutMapping("rules")
    public Object updateRule(@Valid @RequestBody RuleUpdateDTO ruleUpdateDTO) {
        return ruleService.updateRule(ruleUpdateDTO);
    }

    @ApiOperation(value = "查询总线规则")
    @GetMapping("rules/{ruleId}")
    public Object getRuleDetail(@PathVariable("ruleId") Integer ruleId) {
        RuleDTO ruleDTO = ruleService.getRuleDetail(ruleId);
        if (ruleDTO == null) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return ruleDTO;
    }

    @ApiOperation(value = "查询总线规则列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "name", required = true, value = "名称", paramType = "form", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "description", required = true, value = "描述", paramType = "form", dataTypeClass = Long.class)
    })
    @GetMapping("{busId}/rules")
    public IPage getRuleList(@PathVariable Integer busId, Page page, RuleQuery ruleQuery) {
        return ruleService.getRuleList(busId, page, ruleQuery);
    }

    @ApiOperation(value = "删除总线规则")
    @DeleteMapping("rules/{ruleId}")
    public Object deleteRule(@PathVariable("ruleId") Integer ruleId) {
        boolean result = ruleService.deleteRule(ruleId);
        if (!result) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return null;
    }

}
