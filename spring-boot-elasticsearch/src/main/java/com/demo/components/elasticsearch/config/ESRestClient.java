package com.demo.components.elasticsearch.config;

import com.demo.components.elasticsearch.utils.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

/**
 * Elasticsearch Rest client based on RestHighLevelClient
 */
public class ESRestClient implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESRestClient.class);

    private static final String DEFAULT_SCHEME_NAME = "http";

    private static final String SERVER_SPLIT_CHAR = ";";
    private static final String HOST_PORT_SPLIT_CHAR = ":";

    private String environment;
    private ESRestProperties properties;
    private RestHighLevelClient restClient;


    public ESRestClient(String environment, ESRestProperties properties) throws IOReactorException {
        Assert.isTrue(environment != null && !environment.isEmpty(), "ES env is empty!");
        Assert.notNull(properties, "ES(env=" + environment + ") properties is null!");
        Assert.isTrue(StringUtils.isNotBlank(properties.getServers()), "ES(env=" + environment + ") servers is empty!");
        this.environment = environment;
        this.properties = properties;
        this.restClient = buildRestClient();
    }

    private RestHighLevelClient buildRestClient() throws IOReactorException {
        String schema = properties.getSchema();
        if (schema == null || schema.isEmpty()) {
            schema = DEFAULT_SCHEME_NAME;
        }
        String[] servers = properties.getServers().split(SERVER_SPLIT_CHAR);
        HttpHost[] httpHosts = new HttpHost[servers.length];
        for (int i = 0; i < servers.length; i++) {
            String server = servers[i];
            String[] hostAndPort = server.split(HOST_PORT_SPLIT_CHAR);
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
            return httpClientBuilder.setKeepAliveStrategy((response, context) -> {
                Args.notNull(response, "HTTP response");
                final HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    final HeaderElement he = it.nextElement();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                return 30 * 1000;
            }).setConnectionManager(connManager);
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

    public RestHighLevelClient getRestClient() {
        return restClient;
    }

    @Override
    public void destroy() throws Exception {
        if (this.restClient != null) {
            try {
                this.restClient.close();
                logger.info("### ES client(env={}) destroyed successfully!", environment);
            } catch (Exception e) {
                logger.info("### ES client(env={}) destroyed error!", environment);
            }
        }
    }
}