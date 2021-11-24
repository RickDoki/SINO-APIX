package com.sinosdx.service.event.bridge.controller;

import com.sinosdx.common.base.annotation.ResponseResult;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.base.result.enums.ResultCodeEnum;
import com.sinosdx.service.event.bridge.assembler.RuleTargetConverter;
import com.sinosdx.service.event.bridge.controller.dto.RuleSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetDTO;
import com.sinosdx.service.event.bridge.controller.dto.RuleTargetUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.DataParsingQuery;
import com.sinosdx.service.event.bridge.service.IEventTransformService;
import com.sinosdx.service.event.bridge.service.IRuleService;
import com.sinosdx.service.event.bridge.service.IRuleTargetService;
import com.sinosdx.service.event.bridge.utils.RuleUtil;
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
@RequestMapping("buses/rules/targets")
@Api(tags = "规则目标")
public class RuleTargetController {

    @Autowired
    private IRuleTargetService ruleTargetService;

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private IEventTransformService eventTransformService;

    @Autowired(required = false)
    private RuleTargetConverter ruleTargetConverter;

    @ApiOperation(value = "保存总线规则单个目标")
    @PostMapping
    public Object saveRule(@Valid @RequestBody RuleSaveDTO.RuleTargetInfo ruleTargetInfo) {
        //参数校验
        Integer ruleId = ruleTargetInfo.getRuleId();
        if (ruleId == null) {
            return R.fail("规则id不能为空");
        }
        if (ruleService.getRuleById(ruleId) == null) {
            return R.fail("该规则不存在");
        }
        RuleUtil.checkRuleParam(ruleTargetInfo);
        RuleUtil.checkRuleParam(ruleTargetInfo.getCallbackRuleTargetInfo());
        return ruleService.saveSingleTarget(ruleTargetInfo, ruleTargetInfo.getRuleId());
    }

    @ApiOperation(value = "修改规则目标")
    @PutMapping
    public Object updateRuleTarget(@Valid @RequestBody RuleTargetUpdateDTO targetUpdateDTO) {
        if (targetUpdateDTO.getCallbackRuleTargetId() != null) {
            //回调修改
            RuleTargetUpdateDTO callbackTarget = ruleTargetConverter
                    .callbackToUpdate(targetUpdateDTO.getCallbackTarget());
            if (callbackTarget == null) {
                return R.fail("参数不正确，存在CallbackRuleTargetId，但没有对应内容callbackTarget");
            }
            RuleUtil.checkRuleParam(callbackTarget);
            callbackTarget.setId(targetUpdateDTO.getCallbackRuleTargetId());
            ruleTargetService.updateRuleTarget(callbackTarget);
        } else if (targetUpdateDTO.getCallbackTarget() != null) {
            //回调新增
            RuleSaveDTO.CallbackRuleTargetInfo callbackTarget = ruleTargetConverter
                    .callbackTo(targetUpdateDTO.getCallbackTarget());
            Integer callbackRuleId = ruleService.saveCallback(callbackTarget);
            targetUpdateDTO.setCallbackRuleTargetId(callbackRuleId);
        } else {
            //回调删除
            targetUpdateDTO.setCallbackRuleTargetId(0);
        }
        RuleUtil.checkRuleParam(targetUpdateDTO);
        return ruleTargetService.updateRuleTarget(targetUpdateDTO);
    }

    @ApiOperation(value = "查询规则目标")
    @GetMapping("{ruleTargetId}")
    public Object getRuleTargetDetail(@PathVariable("ruleTargetId") Integer ruleTargetId) {
        RuleTargetDTO ruleTargetDTO = ruleTargetService.getRuleTargetDetail(ruleTargetId);
        if (ruleTargetDTO == null) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        if (ruleTargetDTO.getCallbackRuleTargetId() != null) {
            ruleTargetDTO.setCallbackRuleTarget(
                    ruleTargetService.getRuleTargetDetail(ruleTargetDTO.getCallbackRuleTargetId()));
        }
        return ruleTargetDTO;
    }

    @ApiOperation(value = "删除规则目标")
    @DeleteMapping("{id}")
    public Object deleteBus(@PathVariable("id") Integer id) {
        boolean success = ruleTargetService.removeRuleTarget(id);
        if (!success) {
            return R.fail(ResultCodeEnum.RESULT_DATA_NONE);
        }
        return null;
    }

    @ApiOperation(value = "数据表达式匹配-test")
    @PostMapping("test/data-parsing")
    public Object dataParsing(@Valid @RequestBody DataParsingQuery query) {
        return eventTransformService.dataParsing(query.getData(), query.getExpression());
    }

    @ApiOperation(value = "模板替换-test")
    @PostMapping("test/data-template-parsing")
    public Object dataTemplateParsing(@Valid @RequestBody DataParsingQuery query) {
        return eventTransformService
                .dataTemplateParsing(query.getData(), query.getExpression(), query.getTemplate());
    }

}
