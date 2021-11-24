package com.sinosdx.service.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.user.dao.entity.SysRoleOrgEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与组织关联表
 *
 * @author mingsong.song
 * @email mingsong.song@sinodx.com
 * @date 2021-09-18 09:58:37
 */
@Mapper
public interface SysRoleOrgDao extends BaseMapper<SysRoleOrgEntity> {

    void deleteByRoleIds(Long[] roleIds);

}
