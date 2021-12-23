package com.sinosdx.service.user.service;

import com.sinosdx.common.base.result.R;

/**
 * @author wendy
 * @date 2021/12/21
 */
public interface SysClientService {

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    R<Object> queryClientByUserId(Integer sysUserId);
}
