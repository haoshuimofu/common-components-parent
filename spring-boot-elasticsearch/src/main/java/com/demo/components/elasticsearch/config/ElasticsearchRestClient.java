package com.demo.components.elasticsearch.config;

import com.alibaba.fastjson.JSON;
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
        System.err.println(JSON.toJSONString(restProperties, true));
        System.err.println("http pool开启否: " + restProperties.isConnectPoolingEnabled());
        RestClientBuilder builder;
        if (restProperties.isConnectPoolingEnabled()) {
            final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                    .setConnectTimeout(Math.max(restProperties.getConnectTimeout(), 0))
                    .setSoTimeout(Math.max(restProperties.getSocketTimeout(), 0))
                    .setSoKeepAlive(true)
                    .build();
            final PoolingNHttpClientConnectionManager connManager =
                    new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig));
            if (restProperties.getDefaultMaxPerRoute() > 0) {
                connManager.setDefaultMaxPerRoute(1000);
            }
            if (restProperties.getMaxTotal() > 0) {
                connManager.setMaxTotal(1000);
            }
            builder = RestClient.builder(httpHosts)
                    .setHttpClientConfigCallback(callback -> {
                        callback.disableAuthCaching();
                        return callback.setKeepAliveStrategy((response, context) -> {
                            Args.notNull(response, "HTTP response must not be null.");
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
                    })
                    .setRequestConfigCallback(requestConfigBuilder ->
                            requestConfigBuilder
                                    .setConnectTimeout(restProperties.getConnectTimeout() > 0 ? restProperties.getConnectTimeout() : -1)
                                    .setSocketTimeout(restProperties.getSocketTimeout() > 0 ? restProperties.getSocketTimeout() : -1)
                                    .setConnectionRequestTimeout(
                                            restProperties.getConnectionRequestTimeout() > 0 ?
                                                    restProperties.getConnectionRequestTimeout() : -1)
                    );
        } else {
            builder = RestClient.builder(httpHosts)
                    .setRequestConfigCallback(requestConfigBuilder ->
                            requestConfigBuilder
                                    .setConnectTimeout(restProperties.getConnectTimeout() > 0 ? restProperties.getConnectTimeout() : -1)
                                    .setSocketTimeout(restProperties.getSocketTimeout() > 0 ? restProperties.getSocketTimeout() : -1)
                    );
        }
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