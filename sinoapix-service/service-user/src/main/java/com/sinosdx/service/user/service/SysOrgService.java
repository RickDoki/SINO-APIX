package com.sinosdx.service.user.service;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysOrg;

/**
 * @author wendy
 * @date 2021/9/24
 */
public interface SysOrgService {

    /**
     * 更新组织
     *
     * @param sysOrg
     * @return
     */
    R<Object> updateOrg(SysOrg sysOrg);

    /**
     * 查询组织列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryOrgList(String name, Integer limit, Integer offset);

}
