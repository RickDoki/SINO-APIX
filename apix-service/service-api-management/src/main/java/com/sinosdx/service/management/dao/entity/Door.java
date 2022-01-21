package com.sinosdx.service.management.dao.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Door {
    private String title;
    private String description;
}
