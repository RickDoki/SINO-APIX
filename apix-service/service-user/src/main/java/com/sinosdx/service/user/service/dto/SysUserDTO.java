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
