package com.demo.components.httpclient.pooling.config;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2020/9/2 10:04
 */
@Component
public class PoolingHttpClient {

    @Autowired
    @Qualifier("customHttpClientProperties")
    private HttpClientPoolingProperties properties;

    @Autowired
    @Qualifier("customConnectionManager")
    private PoolingHttpClientConnectionManager connectionManager;


}