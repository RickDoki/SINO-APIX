package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 * @date 2021/7/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role_user")
public class SysRoleUser extends Entity<Integer> {

    private Integer userId;

    private Integer roleId;


}
