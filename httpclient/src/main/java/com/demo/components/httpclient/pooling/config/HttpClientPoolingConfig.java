package com.demo.components.httpclient.pooling.config;

import com.demo.components.httpclient.HttpHelper;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2020/9/1 10:14
 */
@Configuration
public class HttpClientPoolingConfig {

    @Bean("customHttpClientProperties")
    @ConfigurationProperties(prefix = "httpclient.custom.pooling")
    public HttpClientPoolingProperties customHttpClientProperties() {
        return new HttpClientPoolingProperties();
    }

    @Bean("customConnectionManager")
    public PoolingHttpClientConnectionManager customConnectionManager(
            @Qualifier("customHttpClientProperties") HttpClientPoolingProperties httpClientProperties) {
        return HttpHelper.connectionManager(httpClientProperties);
    }

}