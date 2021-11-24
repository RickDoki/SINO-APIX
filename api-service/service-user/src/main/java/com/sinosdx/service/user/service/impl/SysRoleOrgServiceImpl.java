package com.sinosdx.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.service.user.dao.entity.SysRoleOrgEntity;
import com.sinosdx.service.user.dao.mapper.SysRoleOrgDao;
import com.sinosdx.service.user.service.SysRoleOrgService;
import com.sinosdx.service.user.utils.PageUtils;
import com.sinosdx.service.user.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleOrgService")
public class SysRoleOrgServiceImpl extends ServiceImpl<SysRoleOrgDao, SysRoleOrgEntity> implements SysRoleOrgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleOrgEntity> page = this.page(
                new Query<SysRoleOrgEntity>().getPage(params),
                new QueryWrapper<SysRoleOrgEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteByRoleIds(Long[] roleIds) {
        baseMapper.deleteByRoleIds(roleIds);
    }

    /**
     * 新建组织时增加默认角色关联
     *
     * @param orgId
     * @param roleIds
     */
    @Override
    public void addSysRoleOrgEntityForOrg(Long orgId, Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysRoleOrgEntity sysRoleOrg = new SysRoleOrgEntity();
            sysRoleOrg.setRoleId(roleId);
            sysRoleOrg.setOrgId(orgId);
            baseMapper.insert(sysRoleOrg);
        }
    }

}