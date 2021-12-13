package com.sinosdx.service.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenjian
 * @create 2021-06-29 19:11
 * @Description   /v1/tenants/${tenant-id}/instance_group 封装接口返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantsGroup {
    private double create_time;
    private String description;
    private int id;
    private String name;
    private String tenant_id;
    private double update_time;
}
