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



