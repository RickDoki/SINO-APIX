package com.sinosdx.service.management.controller.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApplicationNumDTO {
    private Integer subscribedNum;
    private Integer apiNum;
    private Integer applicationNum;
}
