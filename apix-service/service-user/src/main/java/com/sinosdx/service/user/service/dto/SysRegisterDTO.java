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


import com.sinosdx.common.base.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author zhuangyang
 * @date 2020/11/23
 */
@Data
@Accessors(chain = true)
public class SysRegisterDTO {

  @Length(max = 40)
  @ApiModelProperty("账户")
  private String account;

  @Phone
  @NotBlank(message = "手机号不能为空")
  @ApiModelProperty("手机号")
  private String mobile;

  @ApiModelProperty("组织名称")
  private String orgName;

  @Length(min = 0,max = 20,message = "用户名长度必须在{min}和{max}之间")
  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty("用户名")
  private String username;

  @NotBlank(message = "密码不能为空")
  @ApiModelProperty("密码")
  private String password;

  @ApiModelProperty("性别")
  private Integer gender;

  @Email
  @Length(max = 60)
  @ApiModelProperty("邮箱")
  private String email;

  @ApiModelProperty("来源")
  private Integer source;

  @ApiModelProperty("UUID")
//  @NotBlank(message = "UUID不能为空")
  private String uuid;

  @ApiModelProperty("验证码")
//  @NotBlank(message = "验证码不能为空")
  private String verifyCode;
}
