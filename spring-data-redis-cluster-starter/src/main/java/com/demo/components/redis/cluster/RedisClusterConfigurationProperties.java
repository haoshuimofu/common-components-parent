package com.demo.components.redis.cluster;

/**
 * @Author ddmc
 * @Create 2019-04-17 18:08
 */
public class RedisClusterConfigurationProperties {

    private String clusterNodes;
    private int maxRedirects;
    private int commandTimeout;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public int getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }
}