package com.sinosdx.service.user.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeleteDto {
    private List<Long> roleIds;
}
