/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.user.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.sinosdx.common.base.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author zhuangyang
 * @date 2020/11/23
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "系统用户登录表")
@TableName("sys_login")
public class SysLogin extends Entity<Integer> {
    private static final long serialVersionUID = 6072331381516817381L;

    @ApiModelProperty("用户账号名")
    private String account;

    @ApiModelProperty("用户userId")
    private Integer userId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("最后一次修改时间")
    private LocalDateTime passwordResetTime;

    @ApiModelProperty("密码过期时间")
    private LocalDateTime passwordExpireTime;

    @ApiModelProperty("wxOpenId")
    private String wxOpenId;

    @ApiModelProperty("qqOpenId")
    private String qqOpenId;

}
