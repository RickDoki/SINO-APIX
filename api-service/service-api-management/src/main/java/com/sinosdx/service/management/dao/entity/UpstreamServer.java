package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wendy
 * @date 2021/9/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("upstream_server")
public class UpstreamServer extends Entity<Integer> {
    private static final long serialVersionUID = 2630991165645802203L;

    private String name;
    private String description;
    private String protocol;
    private String serverAddress;
    private String port;
    private String prefixPath;
    private String loadBalance;
}
