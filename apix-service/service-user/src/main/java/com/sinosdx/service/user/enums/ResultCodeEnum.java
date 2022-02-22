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
package com.sinosdx.service.user.enums;

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
    PARAMS_MOBILE_IS_BLANK(10005, "手机号参数为空，请重新登录"),
    PARAMS_JWT_DIGESTKEY_IS_BLANK(10006, "jwt或digestkey参数为空，请重新登录"),


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
    PRODUCT_ALREADY_EXISTED(50005, "您已创建过该产品"),
    PRODUCT_ERROE_CREATE_VPC(50006, "vpc子网创建失败"),
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

    GET_USER_TOKEN_ERROR(822001, "获取用户信息失败，请重新登录"),
    USER_REGISTER_USERNAME_EMPTY(822002, "用户名不能为空"),
    USER_REGISTER_MOBILE_EMPTY(822003, "手机号码不能为空"),
    USER_REGISTER_EMAIL_EMPTY(822004, "邮箱不能为空"),
    USER_REGISTER_PASSWORD_EMPTY(822005, "密码不能为空"),
    CREATE_JWT_ERROR(822006, "生成jwt失败"),
    VERIFY_JWT_ERROR(822007, "解析jwt失败"),
    ROLE_IS_EXIST(822008, "角色已存在"),
    ROLE_NO_PREM(822009, "新增角色的权限，已超出你的权限范围"),
    ROLE_NO_ORG(822010, "新增角色,请关联组织"),

    /*
        菜单相关
     */
    MENU_NAME_NOT_EXISTED(822011, "菜单名称不能为空"),
    MENU_PRE_NOT_EXISTED(822012, "上级菜单不能为空"),
    MENU_URL_NOT_EXISTED(822013, "菜单URL不能为空"),
    MENU_PARENT_ONLY_CATALOG(822014, "上级菜单只能为目录类型"),
    MENU_PARENT_ONLY_MENU(822015, "上级菜单只能为菜单类型"),

    ORGANIZATION_IS_EXIST(822016, "该组织已存在，请联系组织管理员添加用户或重新填写组织名"),
    ORGANIZATION_NAME_EMPTY(822017, "组织名称不能为空"),
    PASSWORD_SAME_AS_OLD(822018, "新密码不能与旧密码相同"),
    ORG_NOT_EXISTED(822019, "组织不存在"),
    ORG_NAME_IS_EXISTED(822020, "组织名称已存在"),

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
