package com.sinosdx.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sinosdx.service.user.dao.entity.SysUserOrgEntity;
import com.sinosdx.service.user.utils.PageUtils;

import java.util.Map;

/**
 * 用户与组织
 *
 * @author mingsong.song
 * @email mingsong.song@sinodx.com
 * @date 2021-09-18 09:58:37
 */
public interface SysUserOrgService extends IService<SysUserOrgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

