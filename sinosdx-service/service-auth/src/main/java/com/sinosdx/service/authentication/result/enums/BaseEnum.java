package com.sinosdx.service.authentication.result.enums;

/**
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public interface BaseEnum {

    /**
     * 通过枚举类型和code值获取对应的枚举类型
     *
     * @param enumType
     * @param code
     * @param <T>
     * @return
     */
    static <T extends BaseEnum> T valueOf(Class<? extends BaseEnum> enumType, Integer code) {
        if (enumType == null || code == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            int enumCode = enumConstant.getCode();
            if (code.equals(enumCode)) {
                return enumConstant;
            }
        }
        return null;
    }

    /**
     * 获取枚举索引
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取描述
     *
     * @return
     */
    String getDesc();

}
