package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApplicationLease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApplicationLeaseMapper extends BaseMapper<ApplicationLease> {

    List<Map<String, Object>> queryAllAppLeaseByDeveloper(@Param("developerId") Integer developerId);

    List<String> queryAppCodeListByLessorAppCode(@Param("lessorAppCode") String lessorAppCode);

    List<String> queryClientListByAppCode(@Param("appCode") String appCode);

    Map<String, String> queryOAuthInfo(@Param("lessorAppCode") String lessorAppCode,
                                       @Param("userIdList") List<Integer> userIdList);
}
