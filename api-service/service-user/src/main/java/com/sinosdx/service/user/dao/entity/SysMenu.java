package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "系统菜单表")
@TableName(value = "sys_menu")
public class SysMenu extends Entity<Integer> {

    private Integer parentId;
    private Integer type;
    private String icon;
    private String title;
    private String path;
    private Integer sort;
    private String location;
    private String description;

    @TableField(exist = false)
    private List<SysMenu> subMenus;
    @TableField(exist = false)
    private Integer roleId;
    @TableField(exist = false)
    private Set<Integer> menuIds;

}
