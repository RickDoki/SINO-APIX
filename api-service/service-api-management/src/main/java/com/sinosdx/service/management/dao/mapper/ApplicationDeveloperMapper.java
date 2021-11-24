package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApplicationDeveloper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApplicationDeveloperMapper extends BaseMapper<ApplicationDeveloper> {

    List<Map<String, Object>> queryAppDeveloperList(@Param("appCode") String appCode);
}
