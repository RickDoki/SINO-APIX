package com.sinosdx.service.log.controller;

import com.sinosdx.common.base.base.controller.SuperController;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.AuditLog;
import com.sinosdx.service.log.service.IAuditLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/log/audit")
@Api("审计日志")
public class AuditLogController extends
        SuperController<IAuditLogService, Integer, AuditLog, AuditLog, AuditLog, AuditLog> {

    @Autowired
    private IAuditLogService auditLogService;

    /**
     * 查询审计日志列表
     *
     * @param username
     * @param userId
     * @param eventType
     * @param resourceName
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/list")
    public R<Object> queryAuditLogList(@RequestParam(required = false) String username,
                                       @RequestParam(required = false) Integer userId,
                                       @RequestParam(required = false) String eventType,
                                       @RequestParam(required = false) String resourceName,
                                       @RequestParam(required = false) Long startTime,
                                       @RequestParam(required = false) Long endTime,
                                       @RequestParam(required = false) Integer limit,
                                       @RequestParam(required = false) Integer offset) {
        return auditLogService.queryAuditLogList(username, userId, eventType, resourceName, startTime, endTime, limit, offset);
    }

}
