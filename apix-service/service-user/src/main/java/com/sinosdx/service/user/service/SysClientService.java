package com.sinosdx.service.user.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysUser;

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

    /**
     * 根据clientId查询user
     *
     * @param sysClientId
     * @return
     */
    R<SysUser> queryUserByClientId(Integer sysClientId);
}
