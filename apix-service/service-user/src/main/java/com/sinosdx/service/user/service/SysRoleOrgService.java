package com.sinosdx.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sinosdx.service.user.dao.entity.SysRoleOrgEntity;
import com.sinosdx.service.user.utils.PageUtils;

import java.util.Map;

/**
 * 角色与组织关联表
 *
 * @author mingsong.song
 * @email mingsong.song@sinodx.com
 * @date 2021-09-18 09:58:37
 */
public interface SysRoleOrgService extends IService<SysRoleOrgEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteByRoleIds(Long[] roleIds);

    /**
     * 新建组织时增加默认角色关联
     *
     * @param orgId
     * @param roleIds
     */
    void addSysRoleOrgEntityForOrg(Long orgId, Long[] roleIds);
}

