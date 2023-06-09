package com.webtest.webtest.config;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


/**
 * @author weizhenqiang
 * @date 2023/5/29 18:20
 */
@Configuration
public class RestTemplateConfig {

    public static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(60000);
        factory.setReadTimeout(60000);
        factory.setHttpClient(httpClient);
        return factory;
    }




}
