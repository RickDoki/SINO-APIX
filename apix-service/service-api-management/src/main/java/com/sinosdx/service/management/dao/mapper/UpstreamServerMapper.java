package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.UpstreamServer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wendy
 * @date 2021/9/14
 */
@Mapper
public interface UpstreamServerMapper extends BaseMapper<UpstreamServer> {

    List<Object> queryUpstreamServerList(@Param(value = "name") String name,
                                         @Param(value = "userId") Integer userId,
                                         @Param(value = "limit") Integer limit,
                                         @Param(value = "offset") Integer offset);
}
