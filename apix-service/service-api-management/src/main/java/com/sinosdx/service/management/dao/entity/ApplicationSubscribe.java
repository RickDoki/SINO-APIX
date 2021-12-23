package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("application_subscribe")
public class ApplicationSubscribe extends Entity<Integer> {
    private static final long serialVersionUID = -6977170472232822058L;

    /**
     * 订阅者id
     */
    private Integer subscribeClientId;

    /**
     * 被订阅应用编号
     */
    private String appSubscribedCode;

    /**
     * 被订阅应用id
     */
    private Integer appSubscribedId;

    /**
     * 生成一段订阅code用于网关定位
     */
    private String appClientCode;

}
