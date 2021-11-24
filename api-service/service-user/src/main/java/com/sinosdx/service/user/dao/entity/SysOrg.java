package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * @author wendy
 * @date 2021/9/17
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "系统用户组织表")
@TableName("sys_org")
public class SysOrg extends Entity<Integer> {
    private static final long serialVersionUID = -8694014023956631606L;

    private String name;
    private String code;

}
