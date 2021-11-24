package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色与组织关联表
 *
 * @author mingsong.song
 * @email mingsong.song@sinodx.com
 * @date 2021-09-18 09:58:37
 */
@Data
@TableName("sys_role_org")
@Accessors(chain = true)
public class SysRoleOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * orgID
     */
    private Long orgId;

}
