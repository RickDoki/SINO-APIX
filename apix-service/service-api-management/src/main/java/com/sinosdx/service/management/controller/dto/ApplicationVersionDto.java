package com.sinosdx.service.management.controller.dto;

import com.sinosdx.service.management.dao.entity.Api;
import com.sinosdx.service.management.dao.entity.ApplicationVersion;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ApplicationVersionDto {

    private ApplicationVersion applicationVersion;
    private List<Api> apiList;
}
