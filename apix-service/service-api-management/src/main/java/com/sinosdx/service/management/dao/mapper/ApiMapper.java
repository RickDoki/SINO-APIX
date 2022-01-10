package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.controller.dto.ApiDto;
import com.sinosdx.service.management.dao.entity.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApiMapper extends BaseMapper<Api> {

    List<Map<String, Object>> queryApiList(@Param("apiName") String apiName,
                                           @Param("apiUrl") String apiUrl,
                                           @Param("requestMethod") String requestMethod,
                                           @Param("apiVersion") String apiVersion,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime,
                                           @Param(value = "limit") Integer limit,
                                           @Param(value = "offset") Integer offset,
                                           @Param(value = "developerId") Integer developerId,
                                           @Param("userIdList") List<Integer> userIdList);

    List<Api> queryApiListByAppCode(@Param("appCode") String appCode);

    ApiDto getApiDetail(@Param("apiId") Integer apiId);
}
