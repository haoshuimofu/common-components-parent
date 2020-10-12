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
 * @author wude
 * @date 2020/6/15 11:13
 */
public class ElasticsearchRestClient implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchRestClient.class);

    /**
     * Elasticsearch默认配置
     */
    private static final String DEFAULT_SCHEMA = "http://";
    private static final String DEFAULT_SERVER = "localhost:9200";

    private static final String SERVER_SPLIT_CHAR = ";";
    private static final String HOST_PORT_SPLIT_CHAR = ":";

    /**
     * Elasticsearch配置
     */
    private ElasticsearchRestProperties restProperties;

    /**
     * Elasticsearch RestHighLevelClient实例
     */
    private RestHighLevelClient restClient;

    public ElasticsearchRestClient() throws IOReactorException {
        ElasticsearchRestProperties properties = new ElasticsearchRestProperties();
        properties.setSchema(DEFAULT_SCHEMA);
        properties.setServers(DEFAULT_SERVER);
        this.restClient = buildRestClient();
    }

    public ElasticsearchRestClient(ElasticsearchRestProperties restProperties) throws IOReactorException {
        Assert.notNull(restProperties, "Elasticsearch rest配置为空!");
        Assert.isTrue(StringUtils.isNotBlank(restProperties.getSchema()), "elasticsearch.rest.schema配置为空!");
        Assert.isTrue(StringUtils.isNotBlank(restProperties.getServers()), "elasticsearch.rest.servers配置为空!");
        this.restProperties = restProperties;
        this.restClient = buildRestClient();
    }

    public RestHighLevelClient getRestClient() {
        return restClient;
    }

    private RestHighLevelClient buildRestClient() throws IOReactorException {
        String[] servers = restProperties.getServers().split(SERVER_SPLIT_CHAR);
        HttpHost[] httpHosts = new HttpHost[servers.length];
        for (int i = 0; i < servers.length; i++) {
            String server = servers[i];
            String[] hostAndPort = server.split(HOST_PORT_SPLIT_CHAR);
            httpHosts[i] = new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), restProperties.getSchema());
        }

        RestClientBuilder builder = RestClient.builder(httpHosts);
        builder.setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(restProperties.getConnectTimeout() > 0 ?
                                restProperties.getConnectTimeout() : RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                        .setSocketTimeout(restProperties.getSocketTimeout() > 0 ?
                                restProperties.getSocketTimeout() : RestClientBuilder.DEFAULT_SOCKET_TIMEOUT_MILLIS)
                        .setConnectionRequestTimeout(restProperties.getConnectionRequestTimeout() > 0 ?
                                restProperties.getConnectionRequestTimeout() : RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS));

        final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setConnectTimeout(Math.max(restProperties.getConnectTimeout(), 0))
                .setSoTimeout(Math.max(restProperties.getSocketTimeout(), 0))
                .setSoKeepAlive(true)
                .build();
        final PoolingNHttpClientConnectionManager connManager =
                new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig));
        connManager.setDefaultMaxPerRoute(restProperties.getDefaultMaxPerRoute() > 0 ?
                restProperties.getDefaultMaxPerRoute() : RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE);
        connManager.setMaxTotal(restProperties.getMaxTotal() > 0 ?
                restProperties.getMaxTotal() : RestClientBuilder.DEFAULT_MAX_CONN_TOTAL);

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

    @Override
    public void destroy() throws Exception {
        if (this.restClient != null) {
            try {
                this.restClient.close();
                logger.error("### RestHighLevelClient: instance was destroyed!");
            } catch (Exception e) {
                logger.error("### RestHighLevelClient: An exception occurred when it's instance was destroyed!");
            }
        }
    }
}