package com.sinosdx.service.authentication.result.enums;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hutool.core.map.MapUtil;
import org.springframework.util.StringUtils;

/**
 * BaseEnum枚举工具类
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
public class BaseEnumUtil {

    private static final Map<String, List<BaseEnum>> ENUM_MAP = new LinkedHashMap<>();
    private static final Map<String, Map<Integer, String>> ENUM_CLASS_MAP = new LinkedHashMap<>();

    /**
     * 通过类型获取枚举Map
     *
     * @param clazz
     * @return
     */
    public static Map<Integer, String> getMap(Class<? extends BaseEnum> clazz) {
        return ENUM_CLASS_MAP.get(clazz.getName());
    }

    /**
     * 通过类型获取枚举code集合
     *
     * @param clazz
     * @return
     */
    public static Set<Integer> getCodeSet(Class<? extends BaseEnum> clazz) {
        Map<Integer, String> map = ENUM_CLASS_MAP.get(clazz.getName());
        if (MapUtil.isEmpty(map)) {
            return null;
        }
        return map.keySet();
    }

    /**
     * 通过类型获取枚举desc集合
     *
     * @param clazz
     * @return
     */
    public static Collection<String> getDescList(Class<? extends BaseEnum> clazz) {
        Map<Integer, String> map = ENUM_CLASS_MAP.get(clazz.getName());
        if (MapUtil.isEmpty(map)) {
            return null;
        }
        return map.values();
    }

    /**
     * 通过code获取name
     *
     * @param clazz
     * @param code
     * @return
     */
    public static String getDesc(Class<? extends BaseEnum> clazz, Integer code) {
        Map<Integer, String> map = ENUM_CLASS_MAP.get(clazz.getName());
        if (MapUtil.isEmpty(map)) {
            return null;
        }
        return map.get(code);
    }

    /**
     * 判断code在枚举中是否存在
     *
     * @param clazz
     * @param code
     * @return
     */
    public static boolean exists(Class<? extends BaseEnum> clazz, Integer code) {
        String name = getDesc(clazz, code);
        return !StringUtils.isEmpty(name);
    }

    /**
     * 判断code在枚举中是否不存在
     *
     * @param clazz
     * @param code
     * @return
     */
    public static boolean notExists(Class<? extends BaseEnum> clazz, Integer code) {
        return !exists(clazz, code);
    }

    public static Map<String, List<BaseEnum>> getEnumMap() {
        return ENUM_MAP;
    }

    public static Map<String, Map<Integer, String>> getEnumClassMap() {
        return ENUM_CLASS_MAP;
    }
}
