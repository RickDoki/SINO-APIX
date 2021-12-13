package com.sinosdx.service.event.bridge.utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author pengjiahu
 * @date 2020-12-02 01:40
 * @description
 */
@UtilityClass
public class CollectionCopyUtil {

    public <T> List copyList(List<T> list, Class tClass) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), tClass);
    }

    public Map<String, Object> copyMap(Map map) {
        return JSON.parseObject(JSON.toJSONString(map));
    }

}
