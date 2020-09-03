package com.demo.components.httpclient.pooling.config;

/**
 * @author wude
 * @date 2020/8/31 19:55
 */
public class HttpClientPoolingProperties {

    private int matTotal;
    private int maxPerRoute;
    private int connectionRequestTimeout;
    private int connectTimeout;
    private int socketTimeout;

    public int getMatTotal() {
        return matTotal;
    }

    public void setMatTotal(int matTotal) {
        this.matTotal = matTotal;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
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
}