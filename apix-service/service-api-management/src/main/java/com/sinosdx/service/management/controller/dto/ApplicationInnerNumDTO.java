package com.sinosdx.service.management.controller.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApplicationInnerNumDTO {
    private Long subscribedNum = 0L;
    private Long failNum = 0L;
    private Long requestNum = 0L;
}
