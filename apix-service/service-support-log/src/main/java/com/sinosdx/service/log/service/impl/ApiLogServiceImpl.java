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
package com.sinosdx.service.log.service.impl;

import com.sinosdx.common.base.base.service.SuperServiceImpl;
import com.sinosdx.service.log.dao.entity.ApiLog;
import com.sinosdx.service.log.dao.mapper.ApiLogMapper;
import com.sinosdx.service.log.service.IApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Slf4j
@Service
public class ApiLogServiceImpl extends SuperServiceImpl<ApiLogMapper, ApiLog> implements
        IApiLogService {

}
