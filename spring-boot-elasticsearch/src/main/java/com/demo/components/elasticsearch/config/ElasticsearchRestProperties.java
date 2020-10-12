package com.demo.components.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Elasticsearch集群RESTful配置
 *
 * @author wude
 * @date 2020/6/15 13:05
 */
@ConfigurationProperties(prefix = "elasticsearch.rest")
public class ElasticsearchRestProperties {

    private String schema;
    private String servers;
    private int connectionRequestTimeout;
    private int connectTimeout;
    private int socketTimeout;
    private int defaultMaxPerRoute;
    private int maxTotal;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
}