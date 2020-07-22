package com.demo.components.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wude
 * @date 2020/7/8 11:21
 */
@Component
public class ElasticsearchRestDynamicConfig {

    @Value("${elasticsearch.rest.search.timeout:0}")
    private long searchTimeout;
    @Value("${elasticsearch.rest.search.timeout.max:0}")
    private long searchTimeoutMax;
    @Value("${elasticsearch.rest.retry.on.update.conflict:3}")
    private int retryOnConflict;

    public long getSearchTimeout() {
        return searchTimeout;
    }

    public void setSearchTimeout(long searchTimeout) {
        this.searchTimeout = searchTimeout;
    }

    public long getSearchTimeoutMax() {
        return searchTimeoutMax;
    }

    public void setSearchTimeoutMax(long searchTimeoutMax) {
        this.searchTimeoutMax = searchTimeoutMax;
    }

    public int getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(int retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }
}