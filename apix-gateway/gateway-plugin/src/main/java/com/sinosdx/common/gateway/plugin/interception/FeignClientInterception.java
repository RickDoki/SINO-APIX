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
package com.sinosdx.common.gateway.plugin.interception;

import com.sinosdx.common.base.constants.HeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2021-07-14 15:06
 * @description
 */
@Slf4j
public class FeignClientInterception implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header(HeaderConstant.FEIGN_TOKEN_HEADER, "todo:service room certification");
    }
}
