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
import com.sinosdx.service.management.dao.entity.ApplicationLease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface ApplicationLeaseMapper extends BaseMapper<ApplicationLease> {

    List<Map<String, Object>> queryAllAppLeaseByDeveloper(@Param("developerId") Integer developerId);

    List<String> queryAppCodeListByLessorAppCode(@Param("lessorAppCode") String lessorAppCode);

    List<String> queryClientListByAppCode(@Param("appCode") String appCode);

    Map<String, String> queryOAuthInfo(@Param("lessorAppCode") String lessorAppCode,
                                       @Param("userIdList") List<Integer> userIdList);
}
