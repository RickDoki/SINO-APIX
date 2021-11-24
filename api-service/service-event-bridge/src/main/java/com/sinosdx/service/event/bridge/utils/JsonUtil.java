package com.sinosdx.service.event.bridge.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pengjiahu
 * @date 2020-12-01 13:06
 * @description
 */
@UtilityClass
public class JsonUtil {

    public String getFieldValueFromJsonStr(String jsonStr, String fieldName) {
        List<String> fieldValues = new ArrayList<>();
        String regex = "(?<=(\"" + fieldName + "\":\")).*?(?=(\"))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonStr);
        while (matcher.find()) {
            if (StringUtils.isNotEmpty(matcher.group().trim())) {
                fieldValues.add(matcher.group().trim());
            }
        }
        return fieldValues.get(0);
    }

}
