package com.sinosdx.common.gateway.plugin.utils;

import com.sinosdx.common.base.result.AssertsUtil;
import com.sinosdx.common.gateway.plugin.entity.CacheSupplier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author pengjiahu
 * @date 2021-12-30 14:12
 * @description
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    final RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows
    public <T> T get(String key, CacheSupplier<T> cacheSupplier) {
        AssertsUtil.isTrue(StringUtils.isBlank(key), "key is null,error!");
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            T result = cacheSupplier.get();
            redisTemplate.opsForValue()
                    .set(key, result, cacheSupplier.getExpire(), cacheSupplier.getTimeUnit());
            return result;
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

}
