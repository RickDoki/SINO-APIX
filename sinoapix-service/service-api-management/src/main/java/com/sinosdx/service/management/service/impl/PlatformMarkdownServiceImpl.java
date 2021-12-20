package com.sinosdx.service.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.management.dao.entity.PlatformMarkdown;
import com.sinosdx.service.management.dao.mapper.PlatformMarkdownMapper;
import com.sinosdx.service.management.enums.ResultCodeEnum;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.PlatformMarkdownService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wendy
 * @date 2020/12/24
 */
@Service
public class PlatformMarkdownServiceImpl implements PlatformMarkdownService {

    @Resource
    private PlatformMarkdownMapper platformMarkdownMapper;

    /**
     * 创建新平台文档
     *
     * @param platformMarkdown
     * @return
     */
    @Override
    public R<PlatformMarkdown> createNewMarkdown(PlatformMarkdown platformMarkdown) {
        Long count = platformMarkdownMapper.selectCount(new QueryWrapper<PlatformMarkdown>()
                .eq("plat_name", platformMarkdown.getPlatName()).eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.PLATFORM_MARKDOWN_IS_EXIST);
        }

        platformMarkdownMapper.insert(platformMarkdown);
        return R.success(platformMarkdown);
    }

    /**
     * 修改平台文档
     *
     * @param platName
     * @param markdown
     * @return
     */
    @Override
    public R<PlatformMarkdown> modifyMarkdown(String platName, String markdown) {
        PlatformMarkdown platformMarkdown = platformMarkdownMapper.selectOne(new QueryWrapper<PlatformMarkdown>()
                .eq("plat_name", platName).eq("del_flag", 0));
        if (null == platformMarkdown) {
            return R.fail(ResultCodeEnum.PLATFORM_MARKDOWN_IS_NOT_EXIST);
        }
        platformMarkdown.setMarkdown(markdown);
        platformMarkdownMapper.updateById(platformMarkdown);
        return R.success(platformMarkdown);
    }

    /**
     * 查询平台文档
     *
     * @param platName
     * @return
     */
    @Override
    public R<PlatformMarkdown> queryMarkdown(String platName) {
        PlatformMarkdown platformMarkdown = platformMarkdownMapper.selectOne(new QueryWrapper<PlatformMarkdown>()
                .eq("plat_name", platName).eq("del_flag", 0));
        if (null == platformMarkdown) {
            return R.fail(ResultCodeEnum.PLATFORM_MARKDOWN_IS_NOT_EXIST);
        }
        return R.success(platformMarkdown);
    }
}
