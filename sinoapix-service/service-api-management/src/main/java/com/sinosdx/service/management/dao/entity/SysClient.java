package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2021/12/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SysClient extends Entity<Integer> {
    private static final long serialVersionUID = 5382045453719889915L;

    private Integer resourceId;
    private String resourceType;
    private String code;

}


