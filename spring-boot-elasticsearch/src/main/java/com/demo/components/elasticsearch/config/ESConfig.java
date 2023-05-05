package com.demo.components.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Elasticsearch environment config
 */
@ConfigurationProperties(prefix = "elasticsearch.config")
public class ESConfig {

    private Map<String, ESRestProperties> clusters;
    private int retryOnConflict = 3;

    public Map<String, ESRestProperties> getClusters() {
        return clusters;
    }

    public void setClusters(Map<String, ESRestProperties> clusters) {
        this.clusters = clusters;
    }

    public int getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(int retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }
}