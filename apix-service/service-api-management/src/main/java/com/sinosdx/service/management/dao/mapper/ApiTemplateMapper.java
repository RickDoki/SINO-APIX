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
package com.sinosdx.service.management.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.management.dao.entity.ApiTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApiTemplateMapper extends BaseMapper<ApiTemplate> {

    List<Object> queryApiTemplateByCondition(@Param("name") String name,
                                             @Param("url") String url,
                                             @Param("requestMethod") String requestMethod,
                                             @Param("limit") Integer limit,
                                             @Param("offset") Integer offset);
}
