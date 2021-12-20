package com.sinosdx.service.log.service.impl;

import com.sinosdx.common.base.base.service.SuperServiceImpl;
import com.sinosdx.service.log.dao.entity.ApiLog;
import com.sinosdx.service.log.dao.mapper.ApiLogMapper;
import com.sinosdx.service.log.service.IApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Service
public class ApiLogServiceImpl extends SuperServiceImpl<ApiLogMapper, ApiLog> implements
        IApiLogService {

}
