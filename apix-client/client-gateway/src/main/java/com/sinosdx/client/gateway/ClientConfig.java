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
package com.sinosdx.client.gateway;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Slf4j
public class ClientConfig {

    public static final String GATEWAY_LOGIN_PATH = "/api/appLogin";
    private static final String CONFIG_FILE = "/api-gateway-client.properties";
    private final Properties config = new Properties();

    public ClientConfig() {
        InputStream resourceAsStream;
        try {
            String fileProp = System.getProperty("user.dir").replace("\\", "/") + "/conf/"
                    + "/api-gateway-client.properties";
            fileProp = fileProp.replace("//", "/");
            resourceAsStream = new FileInputStream(fileProp);
        } catch (IOException e) {
            resourceAsStream = null;
        }
        if (null == resourceAsStream) {
            resourceAsStream = getClass().getResourceAsStream("/api-gateway-client.properties");
        }
        try {
            this.config.load(resourceAsStream);
        } catch (IOException e) {
            log.error("读取API网关客户端配置文件出错，请检查文件/api-gateway-client.properties是否在类路径下！", e);
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    log.warn("close /api-gateway-client.properties");
                }
            }
        }
    }

    public Properties getConfiguration() {
        return this.config;
    }

    public String getGatewayAddr() {
        return this.config.getProperty("api-gateway.gatewayAddr");
    }

    public Integer getGatewayPort() {
        Integer port;
        try {
            port = Integer.parseInt(this.config.getProperty("api-gateway.gatewayPort"));
        } catch (Exception e) {
            port = 9000;
        }
        return port;
    }

    public Integer getGatewayPortOfSSL() {
        Integer port;
        try {
            port = Integer.parseInt(this.config.getProperty("api-gateway.gatewayPortOfSSL"));
        } catch (Exception e) {
            port = 9001;
        }
        return port;
    }

    public String getAppID() {
        return this.config.getProperty("api-gateway.appID");
    }

    public String getAppAuthKey() {
        return this.config.getProperty("api-gateway.appAuthKey");
    }

    public String getRequestExpireTime() {
        return this.config.getProperty("api-gateway.requestExpireTime");
    }
}



