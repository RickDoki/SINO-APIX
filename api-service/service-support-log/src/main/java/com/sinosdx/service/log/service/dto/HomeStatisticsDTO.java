package com.sinosdx.service.log.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class HomeStatisticsDTO {
    private List<String> keys;
    private List<Integer> values;
}
