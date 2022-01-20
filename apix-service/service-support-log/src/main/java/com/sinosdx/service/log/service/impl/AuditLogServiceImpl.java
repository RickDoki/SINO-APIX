package com.sinosdx.service.log.service.impl;
import com.sinosdx.common.base.base.service.SuperServiceImpl;
import com.sinosdx.common.base.context.ThreadContext;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.log.constants.Constants;
import com.sinosdx.service.log.consumer.SysUserServiceFeign;
import com.sinosdx.service.log.dao.entity.AuditLog;
import com.sinosdx.service.log.dao.mapper.AuditLogMapper;
import com.sinosdx.service.log.service.IAuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Service
public class AuditLogServiceImpl extends SuperServiceImpl<AuditLogMapper, AuditLog> implements
        IAuditLogService {

    @Resource
    private AuditLogMapper auditLogMapper;

    @Autowired
    private SysUserServiceFeign sysUserService;


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
    @Override
    public R<Object> queryAuditLogList(String username, Integer userId, String eventType, String resourceName,
                                       Long startTime, Long endTime, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }
        if (null == startTime) {
            startTime = DateUtils.addWeeks(new Date(), -1).getTime();
        }
        if (null == endTime) {
            endTime = System.currentTimeMillis();
        }
        // 权限判断
//        List<Integer> userIdList = sysUserService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        if (null == userId) {
            userId = ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID);
        }
        List<Object> list = auditLogMapper.queryAuditLogByCondition(username, userId, eventType, resourceName, startTime, endTime, limit, offset, null);
        // 数据集合
        List<Object> logList = (List<Object>) list.get(0);
        // 数据总量
        Integer total = ((List<Integer>) list.get(1)).get(0);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("logList", logList);
        resultMap.put("total", total);
        return R.success(resultMap);
    }
}
