package com.sinosdx.service.log.enums;

import com.sinosdx.common.base.result.enums.BaseEnum;

/**
 * @author: fanyi
 * @create: 2021-10-28 18:05
 * @description: no description
 */
public enum ResultCodeEnum implements BaseEnum {
    /**
     * 请求接口成功
     */
    SUCCESS(200, "请求成功"),

    NOT_NULL(2000, "不能为空"),
    SERVICE_MAPPER_ERROR(2001, "Mapper类转换异常"),
    BASE_VALID_PARAM(2002, "参数验证异常"),

    TOKEN_NULL(821022, "登录失效，请重新登录"),
    TOKEN_ERROR(4004, "token解析错误"),
    TOKEN_EXPIRED(4005, "token已过期"),

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
    REQUEST_METHOD_IS_INVALID(10005, "请求方式无效"),

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

    /* 应用模块接口错误 */
    CREATE_APPLICATION_ERROR(826011, "创建应用失败"),
    APPLICATION_IS_EXIST(826012, "应用名或编号已存在"),
    APP_BE_USED_OR_USING_OTHER_APP(826013, "应用正在使用其他应用服务或被其他服务使用"),
    APP_VERSION_IS_EXIST(826014, "应用版本已存在"),
    APP_IS_NOT_EXIST(826015, "应用不存在"),
    API_IS_EXIST(826016, "API已存在"),
    API_IS_NOT_EXIST(826017, "API不存在"),
    API_IS_BE_USED(826018, "API正在被应用服务使用"),
    APP_DEVELOPER_IS_ADDED(826019, "应用开发者已添加"),
    APP_DEVELOPER_IS_NOT_EXIST(826020, "应用开发者不存在"),
    NOT_APP_CREATOR(826021, "您不是应用创建者，无法进行成员删除"),
    STATUS_MODIFY_ERROR(826022, "状态修改异常"),
    API_VERSION_IS_EXIST(826023, "API版本已存在"),
    LESSEE_APP_IS_NOT_EXIST(826024, "当前应用未发布，不允许对接"),
    LESSOR_APP_IS_NOT_EXIST(826025, "申请对接的应用不存在"),
    APP_LEASE_IS_EXIST(826026, "已对接过该应用，请勿重复对接"),
    APP_LEASE_IS_NOT_EXIST(826027, "当前对接不存在，请检查数据"),
    PLATFORM_MARKDOWN_IS_EXIST(826028, "该平台文档已存在"),
    PLATFORM_MARKDOWN_IS_NOT_EXIST(826029, "该平台文档不存在"),
    APP_CODE_ERROR(826030, "应用编号中请不要包含'-'"),
    CAN_NOT_DELETE_APP_CREATOR(826031, "无法移除应用创建者"),
    NONE_APP_VERSION(826032, "该应用无可对接版本"),
    APP_CODE_ERROR_CHINESE(826033, "应用编号中请不要包含中文"),
    APP_VERSION_IS_NOT_EXIST(826034, "请先发布应用新版本后再进行上架操作"),
    API_TEMPLATE_IS_NOT_EXIST(826035, "api模板不存在"),
    UPSTREAM_SERVER_IS_EXIST(826036, "上游服务已存在"),
    SERVER_ADDRESS_IS_WRONG(826037, "服务地址格式错误"),
    ROLE_IS_EXIST(822008, "角色已存在"),
    ROLE_NO_PREM(822009, "新增角色的权限，已超出你的权限范围"),
    ROLE_NO_ORG(822010, "新增角色,请关联组织"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    RESOURCE_EXISTED(70002, "资源已存在"),
    RESOURCE_NOT_EXISTED(70003, "资源不存在"),

    NULL_POINT_EX(88888, "空指针异常"),
    SQL_EX(99999, "运行SQL出现异常"),
    /*
          菜单相关
       */
    MENU_NAME_NOT_EXISTED(822011, "菜单名称不能为空"),
    MENU_PRE_NOT_EXISTED(822012, "上级菜单不能为空"),
    MENU_URL_NOT_EXISTED(822013, "菜单URL不能为空"),
    MENU_PARENT_ONLY_CATALOG(822014, "上级菜单只能为目录类型"),
    MENU_PARENT_ONLY_MENU(822015, "上级菜单只能为菜单类型"),


    MANAGER_TENANT_NOT_EXISTED(900000, "租户不能为空");

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
