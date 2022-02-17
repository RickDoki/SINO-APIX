/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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