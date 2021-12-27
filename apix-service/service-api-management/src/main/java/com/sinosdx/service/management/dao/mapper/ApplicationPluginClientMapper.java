package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApplicationPluginClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wendy
 * @date 2021/12/22
 */
@Mapper
public interface ApplicationPluginClientMapper extends BaseMapper<ApplicationPluginClient> {

    List<ApplicationPluginClient> queryByAppSubscribe(@Param("subscribeCode") String subscribeCode);
}
