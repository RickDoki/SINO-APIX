package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApplicationApiGateway;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApplicationApiGatewayMapper extends BaseMapper<ApplicationApiGateway> {

    List<ApplicationApiGateway> queryListByCondition(@Param("gatewayId") String gatewayId,
                                                     @Param("appId") Integer appId,
                                                     @Param("apiId") Integer apiId,
                                                     @Param("urlCode") String urlCode);
}
