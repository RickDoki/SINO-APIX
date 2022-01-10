package com.sinosdx.service.management.controller.dto;

import com.sinosdx.service.management.dao.entity.Api;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationVersionDetailDto {
    private Integer appId;
    private String appCode;
    private String version;
    private String description;
    private String creationDate;
    private String lastUpdateDate;
}
