package com.sinosdx.service.authentication.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wendy
 * @date 2021/1/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "login_history")
public class LoginHistory extends Entity<Integer> {
    private static final long serialVersionUID = 7249981485042499630L;

    private Integer userId;
    private String username;
    private String phone;
    private String clientId;
    private String loginIp;
    private String platformType;
    private LocalDateTime loginTime;
}
