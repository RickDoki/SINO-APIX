package com.sinosdx.service.management.controller.vo;

import com.sinosdx.service.management.dao.entity.ApplicationLease;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2020/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLeaseVo implements Serializable {
    private static final long serialVersionUID = -4366172921919295701L;

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

    public ApplicationLeaseVo(ApplicationLease applicationLease) {
        this.appLesseeId = applicationLease.getAppLesseeId();
        this.appLesseeCode = applicationLease.getAppLesseeCode();
        this.appLessorId = applicationLease.getAppLessorId();
        this.appLessorCode = applicationLease.getAppLessorCode();
        this.appLessorCode = applicationLease.getAppLessorCode();
        this.clientId = applicationLease.getClientId();
        this.clientSecret = applicationLease.getClientSecret();
    }
}
