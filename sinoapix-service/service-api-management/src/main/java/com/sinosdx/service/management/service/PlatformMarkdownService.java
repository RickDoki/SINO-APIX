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
