package com.sinosdx.service.log.dao.mapper;

import com.sinosdx.common.base.base.mapper.SuperMapper;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Repository
public interface GatewayLogMapper extends SuperMapper<GatewayLog> {

    List<Object> queryGatewayLogList(@Param("httpMethod") String httpMethod,
                                     @Param("domain") String domain,
                                     @Param("requestPath") String requestPath,
                                     @Param("startTime") Long startTime,
                                     @Param("endTime") Long endTime,
                                     @Param("appCode") String appCode,
                                     @Param("limit") Integer limit,
                                      @Param("offset") Integer offset);

}
