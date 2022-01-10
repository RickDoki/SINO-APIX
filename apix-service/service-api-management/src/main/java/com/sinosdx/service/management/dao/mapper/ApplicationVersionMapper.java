package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.controller.dto.ApplicationVersionDetailDto;
import com.sinosdx.service.management.dao.entity.ApplicationVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApplicationVersionMapper extends BaseMapper<ApplicationVersion> {
    ApplicationVersionDetailDto queryByIdWithDate(@Param("appVersionId")Integer appVersionId);
}
