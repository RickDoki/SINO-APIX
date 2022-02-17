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
package com.sinosdx.service.management.configuration;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * @author wendy
 * @date 2022/1/19
 */
@Configuration
public class ExecutorServiceConfig {

    /**
     * 计算公式：CPU 核数 / (1 - 阻塞系数 0.8)
     *
     * @return 线程池核心线程数
     */
    public static Integer corePoolSize() {
        int cpuCoreNum = Runtime.getRuntime().availableProcessors();
        return new BigDecimal(cpuCoreNum).divide(new BigDecimal("0.2")).intValue();
    }

    public static Integer maxPoolSize() {
        return corePoolSize() + (corePoolSize() >> 1);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(
                corePoolSize(), maxPoolSize(), 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(4096),
                NAMED_THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());
    }

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("apix-pool-%d").build();

    @Bean
    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), NAMED_THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy());

    }
}
