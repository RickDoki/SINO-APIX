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
package com.sinosdx.service.authentication.result.enums;

import com.sinosdx.common.base.result.enums.BaseEnum;

/**
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public enum ResultCodeEnum implements BaseEnum {
    /**
     * 请求接口成功
     */
    SUCCESS(200, "请求成功"),

    NOT_NULL(2000, "不能为空"),
    SERVICE_MAPPER_ERROR(2001, "Mapper类转换异常"),
    BASE_VALID_PARAM(2002, "参数验证异常"),

    USER_BANNED(820010, "该用户已被禁用，请联系管理员"),
    CREATE_JWT_ERROR(822006, "生成jwt失败"),
    TOKEN_NULL(824003, "登录失效，请重新登录认证"),
    TOKEN_ERROR(824004, "token解析错误"),
    TOKEN_EXPIRED(824005, "token已过期"),
    REVOKE_TOKEN_FAILED(824006, "注销token失败，该token已失效"),
    LOGIN_ERROR(824007, "登录失败"),
    USERNAME_OR_PWD_ERROR(824008, "登录失败，用户名或密码错误"),
    BLACKLIST_IS_NOT_EXIST(824009, "该黑名单不存在"),
    BLACKLIST_IS_EXIST(824010, "该黑名单已存在"),
    BLACKLIST_CONTENT_IS_NOT_IP(824011, "请输入正确的IP地址"),
    CLIENT_ID_ERROR(824012, "客户端信息失效，请检查应用对接"),
    TOKEN_CONVERT_ERROR(824013, "token解析错误"),
    TOKEN_CONVERT_EXPIRED(824014, "token已过期"),
    LOGIN_USERNAME_IS_LOCKED(824015, "登录失败次数过多，账户已被锁定一小时"),
    LOGIN_USER_NOT_COMPANY_USER(824016, "登录用户不是上汽企业用户"),
    PWD_ERROR(824017, "登录失败，密码错误"),
    USER_NOT_REGISTERED(824018, "该用户未注册，请先注册"),
    VERIFY_CODE_ERROR(824019, "验证码错误，请重新输入"),
    VERIFY_CODE_EXPIRED(824020, "验证码已过期，请点击图片刷新"),


    // 验证api签名相关状态码4000-4099
    TIMESTAMP_EMPTY(4010, "时间戳（timestamp）不存在"),
    PARAMS_NOT_JSON(4020, "请求参数不符合json格式"),
    TIME_OUT(4021, "api is time out"),
    SERVICE_METHOD_ERROR(4510, "服务端接口方法异常，请联系接口提供方"),
    CAN_NOT_CHECK(4900, "非法调用，无权查看信息"),


    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    SYSTEM_INNER_TIMEOUT(40002, "请求超时，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULT_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    DATA_TOO_MUCH(50004, "批量新增数据过多"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),
    INTERFACE_IDEMPOTENT(60007, "请勿重复提交"),
    INTERFACE_MAINTAINING(60008, "后台数据维护中"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    RESOURCE_EXISTED(70002, "资源已存在"),
    RESOURCE_NOT_EXISTED(70003, "资源不存在"),

    NULL_POINT_EX(88888, "空指针异常"),
    SQL_EX(99999, "运行SQL出现异常");

    private Integer code;
    private String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.message;
    }

    public ResultCodeEnum build(String msg, Object... param) {
        this.message = String.format(msg, param);
        return this;
    }

    public ResultCodeEnum param(Object... param) {
        this.message = String.format(this.message, param);
        return this;
    }
}
