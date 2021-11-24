package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_version")
public class ApplicationVersion extends Entity<Integer> {
    private static final long serialVersionUID = 7944868742675793517L;

    private Integer appId;
    private String appCode;
    private String version;
    private String description;
}
