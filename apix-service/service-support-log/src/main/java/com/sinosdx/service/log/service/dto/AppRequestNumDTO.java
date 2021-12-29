package com.sinosdx.service.log.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AppRequestNumDTO {
    private Long totalNum;
    private Long okNum;
    private Long failNum;
}
