package com.demo.components.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
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

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> RequestConfig.custom()
                .setConnectTimeout(properties.getConnectTimeout() > 0 ? properties.getConnectTimeout() : RequestConstants.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setSocketTimeout(properties.getSocketTimeout() > 0 ? properties.getSocketTimeout() : RequestConstants.DEFAULT_SOCKET_TIMEOUT_MILLIS)
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeout() > 0 ? properties.getConnectionRequestTimeout() : RequestConstants.DEFAULT_CONNECT_TIMEOUT_MILLIS));

        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setConnectTimeout(Math.max(properties.getConnectTimeout(), 0))
                .setSoTimeout(Math.max(properties.getSocketTimeout(), 0))
                .setSoKeepAlive(true)
                .build();
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig));
        connManager.setDefaultMaxPerRoute(properties.getDefaultMaxPerRoute() > 0 ? properties.getDefaultMaxPerRoute() : RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE);
        connManager.setMaxTotal(properties.getMaxTotal() > 0 ? properties.getMaxTotal() : RestClientBuilder.DEFAULT_MAX_CONN_TOTAL);

        // 设置HttpClientConfigCallback
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.disableAuthCaching();
            /**
             * HTTP Keep-Alive 是指在HTTP协议中保持单个TCP连接持续开放以服务于多个HTTP请求的技术。传统的HTTP/1.0默认每个请求-响应对完成之后就会关闭TCP连接，而每一次新的请求都需要重新建立TCP连接，这会导致额外的延迟和资源消耗，尤其是在网页中包含多个元素（比如图片、脚本文件等）时尤为明显。
             *
             * HTTP/1.1及更高版本改进了这一机制，默认支持持久连接（persistent connections），即通过Keep-Alive功能，允许一个TCP连接上可以连续发送多个HTTP请求和响应，而不需要每次都重新建立连接。这意味着服务器在处理完一个HTTP请求后不会立即关闭连接，而是保持一段时间的活跃状态，以便客户端在同一连接上发送后续的HTTP请求。
             *
             * 在HTTP头部，Connection字段用来控制连接管理行为：
             *
             * 在HTTP/1.0中，客户端希望保持连接时需明确设置 Connection: keep-alive。
             * 在HTTP/1.1中，只要两端都支持持久连接，则默认开启Keep-Alive，除非请求或响应头中明确设置了 Connection: close，表示请求结束后关闭连接。
             * 利用HTTP Keep-Alive能够显著减少网络延迟，并提高Web应用性能，因为它减少了TCP握手和挥手过程的次数，节省了带宽和服务器资源。然而，它也意味着服务器必须维护这些连接直到超时或客户端明确关闭，从而可能增加服务器的内存消耗和并发连接数。
             */
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
            return httpClientBuilder.setKeepAliveStrategy(keepAliveStrategy).setConnectionManager(connManager);
            // return httpClientBuilder.setConnectionManager(connManager);
            // 如果没有自定义ConnectionManager则内部实现还是会创建一个, 并发数用以下两个参数 {@link org.apache.http.impl.nio.client.HttpAsyncClientBuilder}
            //.setMaxConnPerRoute(restProperties.getDefaultMaxPerRoute())
            //.setMaxConnTotal(restProperties.getMaxTotal())
            // 实测这里的DefaultRequestConfig会覆盖RequestConfigCallback
            //.setDefaultRequestConfig(RequestConfig.custom()
            //        .setConnectionRequestTimeout(connectionRequestTimeout)
            //        .setConnectTimeout(connectTimeout)
            //        .setSocketTimeout(socketTimeout)
            //       .build());
        });
        return new RestHighLevelClient(restClientBuilder);
    }

}