package com.sinosdx.service.management.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsVo {

    private List<String> appCodes;
    private Long startTime;
    private Long endTime;
}
