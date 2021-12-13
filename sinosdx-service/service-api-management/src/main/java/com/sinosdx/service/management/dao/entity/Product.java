package com.sinosdx.service.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wendy
 * @date 2021/8/23
 */
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = -58565648503480369L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String code;
}
