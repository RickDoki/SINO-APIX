package com.sinosdx.service.log.controller;

import com.sinosdx.common.base.base.controller.SuperController;
import com.sinosdx.service.log.dao.entity.LoginLog;
import com.sinosdx.service.log.service.ILoginLogService;
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
@RequestMapping("login/log")
@Api("login日志")
public class LoginLogController extends
        SuperController<ILoginLogService, Integer, LoginLog, LoginLog, LoginLog, LoginLog> {

}
