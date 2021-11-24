package com.sinosdx.service.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.user.dao.entity.SysLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuangyang
 * @date 2020-11-23 16:06
 * @description
 */
@Mapper
public interface SysLoginMapper extends BaseMapper<SysLogin> {

    SysLogin queryByMobile(@Param("mobile") String mobile);

}
