package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApiTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApiTemplateMapper extends BaseMapper<ApiTemplate> {

    List<Object> queryApiTemplateByCondition(@Param("name") String name,
                                             @Param("url") String url,
                                             @Param("requestMethod") String requestMethod,
                                             @Param("limit") Integer limit,
                                             @Param("offset") Integer offset);
}
