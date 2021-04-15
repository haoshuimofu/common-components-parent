package com.demo.components.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Elasticsearch environment config
 */
@ConfigurationProperties(prefix = "elasticsearch.config")
public class ESConfig {

    private Map<String, ESRestProperties> environment;
    private int retryOnConflict = 3;

    public Map<String, ESRestProperties> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, ESRestProperties> environment) {
        this.environment = environment;
    }

    public int getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(int retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }

}