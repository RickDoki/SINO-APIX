package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import com.sinosdx.service.management.controller.vo.ApplicationVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("application")
public class Application extends Entity<Integer> {
    private static final long serialVersionUID = -5843525684382545761L;

    private String name;
    private String description;
    private String markdown;
    private String iconUrl;
    private String code;
    private String label;
    private String productId;
    private String isPublished;
    private String creationByUsername;
    private String lastUpdatedByUsername;
    private LocalDateTime publishDate;

    public Application(ApplicationVo applicationVo) {
        this.id = applicationVo.getAppId();
        this.name = applicationVo.getAppName();
        this.code = applicationVo.getAppCode();
        this.productId = applicationVo.getProductId();
        this.label = applicationVo.getLabel().stream().filter(Objects::nonNull).collect(Collectors.joining("/"));
        this.description = applicationVo.getDescription();
        this.markdown = applicationVo.getMarkdown();
        this.iconUrl = applicationVo.getIconUrl();
        this.isPublished = applicationVo.getIsPublished();
        this.publishDate = applicationVo.getPublishDate();
    }
}
