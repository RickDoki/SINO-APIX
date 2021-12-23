package com.sinosdx.service.authentication.result.enums;


import com.sinosdx.service.authentication.exceptions.*;
import org.springframework.http.HttpStatus;

/**
 * 默认映射以下几种，使用时直接new自定义异常即可，如：new DataNotFoundException();
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public enum BaseExceptionEnum {

    /**
     * 无效参数
     */
    PARAMETER_INVALID(ParameterInvalidException.class, HttpStatus.BAD_REQUEST,
            ResultCodeEnum.PARAM_IS_INVALID),

    /**
     * 数据未找到
     */
    NOT_FOUND(DataNotFoundException.class, HttpStatus.NOT_FOUND, ResultCodeEnum.RESULT_DATA_NONE),

    /**
     * 接口方法不允许
     */
    METHOD_NOT_ALLOWED(MethodNotAllowException.class, HttpStatus.METHOD_NOT_ALLOWED,
            ResultCodeEnum.INTERFACE_ADDRESS_INVALID),

    /**
     * 数据已存在
     */
    CONFLICT(DataConflictException.class, HttpStatus.CONFLICT, ResultCodeEnum.DATA_ALREADY_EXISTED),

    /**
     * 无访问权限
     */
    FORBIDDEN(PermissionForbiddenException.class, HttpStatus.FORBIDDEN,
            ResultCodeEnum.PERMISSION_NO_ACCESS),

    /**
     * 远程访问时错误
     */
    REMOTE_ACCESS_ERROR(RemoteAccessException.class, HttpStatus.INTERNAL_SERVER_ERROR,
            ResultCodeEnum.INTERFACE_OUTER_INVOKE_ERROR);

    private Class<? extends BaseException> eClass;

    private HttpStatus httpStatus;

    private ResultCodeEnum resultCode;

    BaseExceptionEnum(Class<? extends BaseException> eClass, HttpStatus httpStatus,
            ResultCodeEnum resultCode) {
        this.eClass = eClass;
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
    }

    public static boolean isSupportHttpStatus(int httpStatus) {
        for (BaseExceptionEnum exceptionEnum : BaseExceptionEnum
                .values()) {
            if (exceptionEnum.httpStatus.value() == httpStatus) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSupportException(Class<?> z) {
        for (BaseExceptionEnum exceptionEnum : BaseExceptionEnum
                .values()) {
            if (exceptionEnum.eClass.equals(z)) {
                return true;
            }
        }

        return false;
    }

    public static BaseExceptionEnum getByHttpStatus(HttpStatus httpStatus) {
        if (httpStatus == null) {
            return null;
        }

        for (BaseExceptionEnum exceptionEnum : BaseExceptionEnum
                .values()) {
            if (httpStatus.equals(exceptionEnum.httpStatus)) {
                return exceptionEnum;
            }
        }

        return null;
    }

    public static BaseExceptionEnum getByEnumClass(Class<? extends BaseException> eClass) {
        if (eClass == null) {
            return null;
        }

        for (BaseExceptionEnum exceptionEnum : BaseExceptionEnum
                .values()) {
            if (eClass.equals(exceptionEnum.eClass)) {
                return exceptionEnum;
            }
        }

        return null;
    }

    public Class<? extends BaseException> getEClass() {
        return eClass;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ResultCodeEnum getResultCode() {
        return resultCode;
    }
}
