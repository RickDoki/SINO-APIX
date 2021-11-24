package com.sinosdx.service.log.service.impl;

import com.sinosdx.common.base.base.service.SuperServiceImpl;
import com.sinosdx.service.log.dao.entity.ErrorLog;
import com.sinosdx.service.log.dao.mapper.ErrorLogMapper;
import com.sinosdx.service.log.service.IErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Service
public class ErrorLogServiceImpl extends SuperServiceImpl<ErrorLogMapper, ErrorLog> implements
        IErrorLogService {

}
