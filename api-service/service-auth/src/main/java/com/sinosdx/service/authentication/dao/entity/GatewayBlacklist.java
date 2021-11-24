package com.sinosdx.service.authentication.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2021/1/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gateway_blacklist")
public class GatewayBlacklist extends Entity<Integer> {
    private static final long serialVersionUID = 2636516948273108887L;

    private String type;
    private String content;
}
