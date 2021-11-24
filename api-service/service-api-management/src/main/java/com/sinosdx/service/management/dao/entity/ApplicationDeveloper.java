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
@TableName("application_developer")
public class ApplicationDeveloper extends Entity<Integer> {
    private static final long serialVersionUID = 619223897602124149L;

    private String appCode;
    private Integer appId;
    private String username;
    private String phone;
    private Integer userId;
    private Boolean isCreator;
}
