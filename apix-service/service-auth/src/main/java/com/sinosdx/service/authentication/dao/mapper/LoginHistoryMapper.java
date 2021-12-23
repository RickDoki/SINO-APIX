package com.sinosdx.service.authentication.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.authentication.dao.entity.LoginHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface LoginHistoryMapper extends BaseMapper<LoginHistory> {
}
