package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.Application;
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
public interface ApplicationMapper extends BaseMapper<Application> {

    List<Map<String, Object>> queryAppVoList(@Param("developerId") Integer developerId,
                                             @Param(value = "appName") String appName,
                                             @Param(value = "appCode") String appCode,
                                             @Param(value = "appId") Integer appId,
                                             @Param("isPublished") String isPublished,
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime,
                                             @Param(value = "limit") Integer limit,
                                             @Param(value = "offset") Integer offset,
                                             @Param(value = "statusList") List<String> statusList,
                                             @Param("userIdList") List<Integer> userIdList);

    Map<String, Object> queryAppDetails(@Param("appCode") String appCode);

    List<Map<String, Object>> queryUsingAppList(@Param("appCode") String appCode);

    Application queryAppByCode(@Param("appCode") String appCode);

    List<Map<String, String>> queryAppNameListForDashboard();

    List<Map<String, String>> queryAppNameAndCodeForLease(@Param("appCode") String appCode,
                                                          @Param(value = "statusList") List<String> statusList,
                                                          @Param("developerId") Integer developerId);

    List<Application> queryAppForLease(@Param("appCode") String appCode,
                                       @Param(value = "statusList") List<String> statusList,
                                       @Param("developerId") Integer developerId);

    Application queryAppByStatus(@Param("appCode") String appCode,
                                       @Param(value = "statusList") List<String> statusList);

    List<Object> querySubscribedAppList(@Param("developerId") Integer developerId,
                                        @Param(value = "appName") String appName,
                                        @Param(value = "appCode") String appCode,
                                        @Param(value = "appId") Integer appId,
                                        @Param(value = "limit") Integer limit,
                                        @Param(value = "offset") Integer offset,
                                        @Param(value = "sysClientId") Integer sysClientId,
                                        @Param("userIdList") List<Integer> userIdList);

    /**
     * 查询订阅当前应用的应用列表
     *
     * @param appCode
     * @param userIdList
     * @return
     */
    List<Application> querySubscribeCurrentAppList(@Param(value = "appCode") String appCode,
                                                   @Param("userIdList") List<Integer> userIdList);

    /**
     * 根据appId查询api列表
     * @param appId
     * @return
     */
    List<Map<String, String>> queryApiByAppId(@Param(value = "appId") String appId);

    /**
     * 根据appId查询订阅者code
     * @param appId
     * @return
     */
    List<String> querySubscribeCodeByAppId(@Param(value = "appId") String appId);
}
