package com.sinosdx.service.log.service;

import com.sinosdx.common.base.base.service.SuperService;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.dao.entity.AuditLog;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
public interface IAuditLogService extends SuperService<AuditLog> {

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
    R<Object> queryAuditLogList(String username, Integer userId, String eventType, String resourceName,
                                Long startTime, Long endTime, Integer limit, Integer offset);
}
