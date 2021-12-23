package com.sinosdx.service.log.controller;

import com.sinosdx.common.base.base.controller.SuperController;
import com.sinosdx.service.log.dao.entity.ErrorLog;
import com.sinosdx.service.log.service.IErrorLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Validated
@RestController
@RequestMapping("error/log")
@Api("error日志")
public class ErrorLogController extends
        SuperController<IErrorLogService, Integer, ErrorLog, ErrorLog, ErrorLog, ErrorLog> {

}
