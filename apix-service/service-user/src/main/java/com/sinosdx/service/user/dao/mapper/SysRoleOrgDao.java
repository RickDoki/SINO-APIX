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
