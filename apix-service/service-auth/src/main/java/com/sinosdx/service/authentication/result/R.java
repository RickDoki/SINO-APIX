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
package com.sinosdx.service.authentication.result;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.sinosdx.service.authentication.exceptions.BaseException;
import com.sinosdx.service.authentication.result.enums.BaseEnum;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author pengjiahu
 * @date 2020-08-04 12:59
 * @description
 */
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
@Setter
@SuppressWarnings("ALL")
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = -3948389268046368059L;

    private static final Integer CODE_SUCCESS = ResultCodeEnum.SUCCESS.getCode();
    private static final String MSG_SUCCESS = ResultCodeEnum.SUCCESS.getDesc();
    private static final Integer CODE_ERROR = ResultCodeEnum.SYSTEM_INNER_ERROR.getCode();
    private static final String MSG_ERROR = ResultCodeEnum.SYSTEM_INNER_ERROR.getDesc();

    @ApiModelProperty(value = "响应编码:0/200-请求处理成功")
    private int code;

    @ApiModelProperty(value = "响应数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @ApiModelProperty(value = "提示消息")
    private String msg;

    @ApiModelProperty(value = "请求路径")
    private String path;

    @ApiModelProperty(value = "附加数据")
    @JsonIgnore
    private Map<Object, Object> extra;

    /**
     * 是否执行默认操作
     */
    @JsonIgnore
    private Boolean defExec = true;

    @ApiModelProperty(value = "响应时间戳")
    private long timestamp = ZonedDateTime.now().toInstant().toEpochMilli();

    private R() {
        this.defExec = false;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
    }

    public R(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.msg = baseEnum.getDesc();
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.extra = getExtra();
        this.defExec = false;
    }

    public R(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
        this.defExec = false;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.extra = getExtra();
    }

    public R(int code, T data, String msg) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.defExec = false;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.extra = getExtra();
    }

    public R(int code, T data, String msg, String path) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.defExec = false;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.extra = getExtra();
        this.path = path;
    }

    public R(int code, T data, String msg, boolean defExec) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.defExec = defExec;
        this.timestamp = ZonedDateTime.now().toInstant().toEpochMilli();
        this.extra = getExtra();
    }

    public static <T> R<T> success(T data) {
        return new R<>(CODE_SUCCESS, data, convertMethodMsg(true));
    }

    public static <T> R<T> success() {
        return new R<>(CODE_SUCCESS, convertMethodMsg(true));
    }

    public static <T> R<T> successDef(T data, String msg) {
        return new R<>(CODE_SUCCESS, data, msg, true);
    }

    public static <T> R<T> successDef(T data) {
        return new R<>(CODE_SUCCESS, data, convertMethodMsg(true), true);
    }

    public static <T> R<T> successDef() {
        return new R<>(CODE_SUCCESS, null, MSG_SUCCESS, true);
    }

    public static <T> R<T> fail() {
        return new R<>(CODE_ERROR, convertMethodMsg(false));
    }

    public static <T> R<T> fail(String error) {
        return new R<>(CODE_ERROR, null, error);
    }

    public static <T> R<T> fail(String error, String path) {
        return new R<>(CODE_ERROR, null, error, path);
    }

    public static <T> R<T> fail(String error, int code) {
        return new R<>(code, null, error);
    }

    public static <T> R<T> fail(int code, String error) {
        return new R<>(code, null, error);
    }

    public static <T> R<T> fail(String error, Object... args) {
        String message = StringUtils.isEmpty(error) ? MSG_ERROR : error;
        return new R<>(CODE_ERROR, null, String.format(message, args));
    }

    public static <T> R<T> fail(ResultCodeEnum codeEnum) {
        System.out.println(codeEnum.getCode());
        return new R<>(codeEnum.getCode(), null, codeEnum.getDesc());
    }

    public static <T> R<T> fail(ResultCodeEnum codeEnum, String path) {
        return new R<>(codeEnum.getCode(), null, codeEnum.getDesc(), path);
    }

    public static <T> R<T> fail(BaseException exception) {
        if (exception == null) {
            return fail();
        }
        return new R<>(Integer.parseInt(exception.getCode()), null, exception.getMessage());
    }

    public static <T> R<T> fail(Throwable throwable) {
        return fail(throwable != null ? throwable.getMessage() : MSG_ERROR);
    }

    public static <T> R<T> status(boolean flag) {
        return flag ? success() : fail();
    }

    public static <T> R<T> timeout() {
        return fail(ResultCodeEnum.SYSTEM_INNER_TIMEOUT);
    }

    private static String convertMethodMsg(boolean success) {
        String successMessage;
        String errorMessage;
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String method = "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            method = request.getMethod();
        }
        switch (method.toUpperCase()) {
            case "GET":
                successMessage = "获取成功";
                errorMessage = "获取失败";
                break;
            case "POST":
                successMessage = "提交成功";
                errorMessage = "提交失败";
                break;
            case "PUT":
                successMessage = "更新成功";
                errorMessage = "更新失败";
                break;
            case "DELETE":
                successMessage = "删除成功";
                errorMessage = "删除失败";
                break;
            default:
                successMessage = MSG_SUCCESS;
                errorMessage = MSG_ERROR;
        }

        if (success) {
            return successMessage;
        }
        return errorMessage;
    }

    public boolean isSuccess() {
        return this.code == CODE_SUCCESS;
    }

    public boolean notSuccess() {
        return !this.isSuccess();
    }

    public boolean isData() {
        return this.data != null;
    }

    public boolean notNull() {
        return isSuccess() && isData();
    }

    public R<T> put(String key, Object value) {
        if (this.extra == null) {
            this.extra = new HashMap<>(10);
        }
        this.extra.put(key, value);
        return this;
    }

    public R<T> putAll(Map<Object, Object> extra) {
        if (this.extra == null) {
            this.extra = new HashMap<>(10);
        }
        this.extra.putAll(extra);
        return this;
    }

    public Object parseResultData() {
        if (getData() != null && isSuccess()) {
            return getData();
        }
        throw new BaseException(getMsg());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
