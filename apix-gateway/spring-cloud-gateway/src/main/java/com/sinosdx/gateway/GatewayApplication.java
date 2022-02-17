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
package com.sinosdx.gateway;

import com.sinosdx.gateway.configuration.ApplicationListenerStarted;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author pengjiahu
 * @date 2020/8/4 15:41
 * @description
 */
@EnableAsync
@SpringBootApplication
@ComponentScan(value = {"com.sinosdx.gateway", "com.sinosdx.common.gateway"})
public class GatewayApplication {

    public static void main(String[] args) {
//        System.setProperty("csp.sentinel.app.type", "1");//dashboard监控用
        SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
        springApplication.addListeners(new ApplicationListenerStarted());
        springApplication.run(args);
    }
}

