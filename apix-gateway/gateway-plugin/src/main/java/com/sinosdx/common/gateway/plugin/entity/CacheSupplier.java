package com.sinosdx.common.gateway.plugin.entity;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pengjiahu
 * @date 2021-12-30 14:16
 * @description
 */
@AllArgsConstructor
@Getter
@Setter
public class CacheSupplier<T> {

    private int expire;

    private TimeUnit timeUnit;

    Supplier<T> supplier;

    public T get() {
        return this.supplier.get();
    }
}
