package com.demo.components.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Elasticsearch Rest client based on RestHighLevelClient
 * connectionTimeout: 建立TCP连接的超时时间。客户端尝试向服务端发起连接，如果在指定的时间内没有得到服务器的响应，则会抛出连接超时异常。
 * connectionRequestTimeout: 从连接池中获取连接的超时时间。当客户端向连接池请求连接时，如果在指定的时间内没有获得空闲可用的连接，则会抛出连接请求超时异常。
 */
public class ESRestClientBuilder {

    private static final String DEFAULT_SCHEME_NAME = "http";
    private static final String SERVER_SPLIT_CHAR = ";";
    private static final String HOST_PORT_SPLIT_CHAR = ":";

    public static RestHighLevelClient buildRestClient(ESRestProperties properties) throws IOReactorException {
        String schema = properties.getSchema();
        if (schema == null || schema.isEmpty()) {
            schema = DEFAULT_SCHEME_NAME;
        }
        String[] servers = properties.getServers().split(SERVER_SPLIT_CHAR);
        HttpHost[] httpHosts = new HttpHost[servers.length];
        for (int i = 0; i < servers.length; i++) {
            String[] hostAndPort = servers[i].split(HOST_PORT_SPLIT_CHAR);
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), schema);
        }

        RestClientBuilder builder = RestClient.builder(httpHosts);
        builder.setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(properties.getConnectTimeout() > 0 ?
                                properties.getConnectTimeout() : RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                        .setSocketTimeout(properties.getSocketTimeout() > 0 ?
                                properties.getSocketTimeout() : RestClientBuilder.DEFAULT_SOCKET_TIMEOUT_MILLIS)
                        .setConnectionRequestTimeout(properties.getConnectionRequestTimeout() > 0 ?
                                properties.getConnectionRequestTimeout() : RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS));

        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setConnectTimeout(Math.max(properties.getConnectTimeout(), 0))
                .setSoTimeout(Math.max(properties.getSocketTimeout(), 0))
                .setSoKeepAlive(true)
                .build();
        final PoolingNHttpClientConnectionManager connManager =
                new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig));
        connManager.setDefaultMaxPerRoute(properties.getDefaultMaxPerRoute() > 0 ?
                properties.getDefaultMaxPerRoute() : RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE);
        connManager.setMaxTotal(properties.getMaxTotal() > 0 ?
                properties.getMaxTotal() : RestClientBuilder.DEFAULT_MAX_CONN_TOTAL);

        // 设置HttpClientConfigCallback
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.disableAuthCaching();
            ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    long keepAliveDuration = super.getKeepAliveDuration(response, context);
                    if (keepAliveDuration == -1) {
                        // Keep connections alive 5 seconds if a keep-alive value has not be explicitly set by the server
                        keepAliveDuration = 5 * 1000L;
                    }
                    return keepAliveDuration;
                }
            };
            return httpClientBuilder
                    .setKeepAliveStrategy(keepAliveStrategy)
                    .setConnectionManager(connManager);
            // return httpClientBuilder.setConnectionManager(connManager);
            // 如果没有自定义ConnectionManager则内部实现还是会创建一个, 并发数用一下两个参数
            //.setMaxConnPerRoute(restProperties.getDefaultMaxPerRoute())
            //.setMaxConnTotal(restProperties.getMaxTotal())
            // 实测这里的DefaultRequestConfig会覆盖RequestConfigCallback
            //.setDefaultRequestConfig(RequestConfig.custom()
            //        .setConnectionRequestTimeout(connectionRequestTimeout)
            //        .setConnectTimeout(connectTimeout)
            //        .setSocketTimeout(socketTimeout)
            //       .build());
        });
        return new RestHighLevelClient(builder);
    }

}