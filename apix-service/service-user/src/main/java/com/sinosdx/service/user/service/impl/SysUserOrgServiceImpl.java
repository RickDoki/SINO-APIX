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