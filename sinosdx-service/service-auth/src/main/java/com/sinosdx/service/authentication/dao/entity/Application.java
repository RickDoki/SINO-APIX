package com.sinosdx.service.authentication.dao.entity;

import com.sinosdx.common.base.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Application extends Entity<Integer> {
    private static final long serialVersionUID = -5843525684382545761L;

    private String name;
    private String description;
    private String code;
    private String isPublished;
    private String creationByUsername;
    private String lastUpdatedByUsername;

}
