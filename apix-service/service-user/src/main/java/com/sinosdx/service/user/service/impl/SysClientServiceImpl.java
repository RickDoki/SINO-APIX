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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysClient;
import com.sinosdx.service.user.dao.entity.SysUser;
import com.sinosdx.service.user.dao.mapper.SysClientMapper;
import com.sinosdx.service.user.dao.mapper.SysUserMapper;
import com.sinosdx.service.user.enums.SysClientEnum;
import com.sinosdx.service.user.service.SysClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wendy
 * @date 2021/12/21
 */
@Service
@Slf4j
public class SysClientServiceImpl implements SysClientService {

    @Resource
    private SysClientMapper sysClientMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    @Override
    public R<Object> queryClientByUserId(Integer sysUserId) {
        SysClient sysClient = sysClientMapper.selectOne(new LambdaQueryWrapper<SysClient>()
                .eq(SysClient::getResourceId, sysUserId)
                .eq(SysClient::getResourceType, SysClientEnum.USER.getName()));
        return R.success(sysClient);
    }

    /**
     * 根据clientId查询user
     *
     * @param sysClientId
     * @return
     */
    @Override
    public R<SysUser> queryUserByClientId(Integer sysClientId) {
        return R.success(sysUserMapper.queryByClientId(sysClientId));
    }
}
