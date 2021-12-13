package com.sinosdx.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sinosdx.service.user.dao.entity.SysUserOrgEntity;
import com.sinosdx.service.user.dao.mapper.SysUserOrgDao;
import com.sinosdx.service.user.service.SysUserOrgService;
import com.sinosdx.service.user.utils.PageUtils;
import com.sinosdx.service.user.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service("sysUserOrgService")
public class SysUserOrgServiceImpl extends ServiceImpl<SysUserOrgDao, SysUserOrgEntity> implements SysUserOrgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserOrgEntity> page = this.page(
                new Query<SysUserOrgEntity>().getPage(params),
                new QueryWrapper<SysUserOrgEntity>()
        );

        return new PageUtils(page);
    }

}