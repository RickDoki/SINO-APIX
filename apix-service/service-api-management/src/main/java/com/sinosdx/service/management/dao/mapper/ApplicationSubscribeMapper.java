package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.controller.dto.ApplicationSubscribeDto;
import com.sinosdx.service.management.dao.entity.ApplicationSubscribe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wendy
 * @date 2021/12/21
 */
@Mapper
public interface ApplicationSubscribeMapper extends BaseMapper<ApplicationSubscribe> {

    ApplicationSubscribeDto querySubscribeDate(@Param("subscribeClientId")Integer subscribeClientId, @Param("appSubscribedCode")String appSubscribedCode);
}
