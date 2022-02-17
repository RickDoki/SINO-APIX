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
