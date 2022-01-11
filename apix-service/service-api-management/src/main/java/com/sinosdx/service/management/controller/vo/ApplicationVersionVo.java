package com.sinosdx.service.management.controller.vo;

import com.sinosdx.service.management.dao.entity.ApplicationVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wendy
 * @date 2020/12/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersionVo implements Serializable {
    private static final long serialVersionUID = 677629965656735742L;

    private Integer appId;
    private String appCode;
    private Integer appVersionId;
    private String appVersion;
    private String versionDesc;
    private String apiIds;
    private String markdown;
    private List<ApiVo> apiList;

    public ApplicationVersionVo(ApplicationVersion applicationVersion, List<ApiVo> apiVoList) {
        this.appId = applicationVersion.getAppId();
        this.appCode = applicationVersion.getAppCode();
        this.appVersionId = applicationVersion.getId();
        this.appVersion = applicationVersion.getVersion();
        this.versionDesc = applicationVersion.getDescription();
        this.markdown = applicationVersion.getMarkdown();
        this.apiList = apiVoList;
    }
}
