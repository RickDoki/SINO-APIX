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
@TableName("application_lease")
public class ApplicationLease extends Entity<Integer> {
    private static final long serialVersionUID = -6977170472232822058L;

    /**
     * 承租应用编号
     */
    private String appLesseeCode;

    /**
     * 承租应用id
     */
    private Integer appLesseeId;

    /**
     * 出租应用编号(获取token的client_id)
     */
    private String appLessorCode;

    /**
     * 出租应用id
     */
    private Integer appLessorId;

    /**
     * 获取token的client_id
     */
    private String clientId;

    /**
     * 获取token的client_secret
     */
    private String clientSecret;

    private String creationByUsername;
    private String lastUpdatedByUsername;
    private String appLesseeName;
    private String appLessorName;
}
