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
//@EnableConfigurationProperties(value = {HttpClientProperties.class})
public class HttpClientPoolingConfig {

    //@Autowired
    //@Qualifier("httpClientProperties")
    //private HttpClientProperties httpClientProperties;

    public PoolingHttpClientConnectionManager connectionManager(HttpClientPoolingProperties properties) {
        return HttpHelper.connectionManager(properties);
    }

    @Bean("customHttpClientProperties")
    @ConfigurationProperties(prefix = "httpclient.custom.polling")
    public HttpClientPoolingProperties customHttpClientProperties() {
        return new HttpClientPoolingProperties();
    }

    @Bean("customConnectionManager")
    public PoolingHttpClientConnectionManager customConnectionManager(
            @Qualifier("customHttpClientProperties") HttpClientPoolingProperties httpClientProperties) {
        return HttpHelper.connectionManager(httpClientProperties);
    }

}