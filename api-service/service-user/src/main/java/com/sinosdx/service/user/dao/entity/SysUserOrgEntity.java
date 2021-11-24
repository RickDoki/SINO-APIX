package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户与组织
 *
 * @author mingsong.song
 * @email mingsong.song@sinodx.com
 * @date 2021-09-18 09:58:37
 */
@Data
@TableName("sys_user_org")
public class SysUserOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * orgID
     */
    private Long orgId;

}
