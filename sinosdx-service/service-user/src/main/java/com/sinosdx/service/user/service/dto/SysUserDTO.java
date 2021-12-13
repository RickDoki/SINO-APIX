package com.sinosdx.service.user.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhuangyang
 * 接口实体dto
 */
@Data
@Accessors(chain = true)
public class SysUserDTO {

    private Integer id;

    private String account;

    private String username;

    private Integer gender;

    private String phone;

    private String email;

    private String source;

    private Boolean enabled;

    private List<SysRoleDTO> roles;
}
