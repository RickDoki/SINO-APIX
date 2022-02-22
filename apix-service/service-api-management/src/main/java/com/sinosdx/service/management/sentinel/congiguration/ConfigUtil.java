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
package com.sinosdx.service.management.sentinel.congiguration;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.sinosdx.service.management.sentinel.entity.ApiDefinitionEntity;
import com.sinosdx.service.management.sentinel.entity.RuleDefinitionEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author shenjian
 * @create 2021-12-30 17:19
 * @Description
 */
@Configuration
public class ConfigUtil {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${spring.cloud.nacos.config.username}")
    private String username;

    @Value("${spring.cloud.nacos.config.password}")
    private String password;


    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,serverAddr);
        properties.setProperty(PropertyKeyConst.NAMESPACE,namespace);
        properties.setProperty(PropertyKeyConst.USERNAME,username);
        properties.setProperty(PropertyKeyConst.PASSWORD,password);
        return ConfigFactory.createConfigService(properties);
    }

    @Bean
    public Converter<List<ApiDefinitionEntity>, String> apiEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<String, List<ApiDefinitionEntity>> apiDecoder(){
        return s -> JSON.parseArray(s, ApiDefinitionEntity.class);
    }
    @Bean
    public Converter<List<RuleDefinitionEntity>, String> ruleEncoder(){return JSON::toJSONString; }

    @Bean
    public Converter<String, List<RuleDefinitionEntity>> ruleDecoder(){
        return s -> JSON.parseArray(s, RuleDefinitionEntity.class);
    }


}