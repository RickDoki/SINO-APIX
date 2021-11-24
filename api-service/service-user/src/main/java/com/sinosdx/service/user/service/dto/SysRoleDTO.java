package com.sinosdx.service.user.service.dto;

import com.sinosdx.common.base.annotation.ValueIn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysRoleDTO {

  private Integer id;

  @Length(max = 40)
  @NotBlank(message = "角色名称不能为空")
  @ApiModelProperty("角色名称")
  private String name;

  @ApiModelProperty("描述")
  private String describe;

  @ValueIn(values = {"50010 超级管理员 50011 业务管理员 50012 开发者 50013 其他"})
  @ApiModelProperty("角色类型")
  private Integer dsType;

  private Integer userId;

  @ApiModelProperty("菜单ID")
  private List<Integer> menus;
}
