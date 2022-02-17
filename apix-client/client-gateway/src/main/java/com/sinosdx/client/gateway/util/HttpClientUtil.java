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
package com.sinosdx.client.gateway.util;

import com.sinosdx.client.gateway.http.Protocol;
import lombok.NoArgsConstructor;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@NoArgsConstructor
public class HttpClientUtil {

    public static CloseableHttpClient createHttpSSLClient(Integer maxConn, Integer maxPerRoute) {
        SSLContextBuilder builder = new SSLContextBuilder();
        CloseableHttpClient httpsClient = null;
        try {
            builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new String[]{"SSLv3", "TLSv1", "TLSv1.2"}, null,
                    NoopHostnameVerifier.INSTANCE);
            RegistryBuilder<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.create();
            schemeRegistry.register(Protocol.http.name(), new PlainConnectionSocketFactory());
            schemeRegistry.register(Protocol.https.name(), sslsf);
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                    schemeRegistry.build());
            cm.setMaxTotal(maxConn);
            cm.setDefaultMaxPerRoute(maxPerRoute);
            httpsClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
                    .setConnectionManagerShared(true).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException var7) {
            var7.printStackTrace();
        }
        return httpsClient;
    }
}



