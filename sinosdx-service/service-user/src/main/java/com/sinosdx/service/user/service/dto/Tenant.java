package com.sinosdx.service.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenjian
 * @create 2021-06-25 9:53
 * @Description tenants/map 接口返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    /**
     * 租户名称
     */
    private String name;
    /**
     * 租户shortname/seqno
     */
    private String shortName;
    /**
     * 租户描述
     */
    private String description;
    /**
     * 租户uuid
     */
    private String id;
}
