package com.sinosdx.service.log.dao.mapper;

import com.sinosdx.common.base.base.mapper.SuperMapper;
import com.sinosdx.service.log.dao.entity.AuditLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Repository
public interface AuditLogMapper extends SuperMapper<AuditLog> {

    List<Object> queryAuditLogByCondition(@Param("username") String username,
                                          @Param("userId") Integer userId,
                                          @Param("eventType") String eventType,
                                          @Param("resourceName") String resourceName,
                                          @Param("startTime") Long startTime,
                                          @Param("endTime") Long endTime,
                                          @Param("limit") Integer limit,
                                          @Param("offset") Integer offset,
                                          @Param("userIdList") List<Integer> userIdList);

}
