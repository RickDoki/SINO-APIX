package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2021/12/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_plugin_client")
public class ApplicationPluginClient extends Entity<Integer> {
    private static final long serialVersionUID = 8999037431641813357L;

    private Integer sysClientId;
    private Integer appPluginId;
    private String pluginType;
    /**
     * 发布到网关的配置（包括服务发布方和订阅方）
     */
    private String pluginParams;
}
