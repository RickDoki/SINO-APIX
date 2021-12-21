package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务插件
 *
 * @author wendy
 * @date 2021/12/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_plugin")
public class ApplicationPlugin extends Entity<Integer> {
    private static final long serialVersionUID = -7133398603981531070L;

    private Integer appId;
    private String appCode;
    private String pluginType;
    private String pluginParams;
    private Integer enabled;

}
