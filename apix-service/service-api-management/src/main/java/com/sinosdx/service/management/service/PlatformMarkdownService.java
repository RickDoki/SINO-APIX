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
package com.sinosdx.service.management.service;

import com.sinosdx.service.management.dao.entity.PlatformMarkdown;
import com.sinosdx.service.management.result.R;

/**
 * @author wendy
 * @date 2020/12/24
 */
public interface PlatformMarkdownService {

    /**
     * 创建新平台文档
     *
     * @param platformMarkdown
     * @return
     */
    R<PlatformMarkdown> createNewMarkdown(PlatformMarkdown platformMarkdown);

    /**
     * 修改平台文档
     *
     * @param platName
     * @param markdown
     * @return
     */
    R<PlatformMarkdown> modifyMarkdown(String platName, String markdown);

    /**
     * 查询平台文档
     *
     * @param platName
     * @return
     */
    R<PlatformMarkdown> queryMarkdown(String platName);
}
