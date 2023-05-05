package com.demo.components.elasticsearch.config;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Elasticsearch Rest Client container for multi-cluster
 *
 * @author wude
 * @date 2021/4/15 11:47
 */
public class ESRestClientContainer implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESRestClientContainer.class);

    private ESConfig esConfig;
    private Map<String, RestHighLevelClient> restClients = new HashMap<>();

    public ESRestClientContainer(ESConfig esConfig) throws Exception {
        this.esConfig = esConfig;
        LOGGER.info("### {}: {}", ESConfig.CONFIG_PREFIX, JSON.toJSONString(esConfig));

        if (esConfig.getClusters() != null && !esConfig.getClusters().isEmpty()) {
            for (Map.Entry<String, ESRestProperties> entry : esConfig.getClusters().entrySet()) {
                RestHighLevelClient client = ESRestClientBuilder.buildRestClient(entry.getValue());
                try {
                    boolean success = client.ping(RequestOptions.DEFAULT);
                    if (!success) {
                        throw new BeanCreationException(String.format("Elasticsearch cluster ping failed, cluster=%s", entry.getKey()));
                    }
                    restClients.put(entry.getKey(), client);
                } catch (IOException e) {
//                    throw new BeanCreationException(String.format("Elasticsearch cluster ping failed, cluster=%s", entry.getKey()), e);
                }
            }
        }
    }

    public ESConfig getEsConfig() {
        return esConfig;
    }

    public RestHighLevelClient restHighLevelClient(String cluster) {
        return Optional.of(restClients.get(cluster))
                .orElseThrow(() -> new NullPointerException("Elasticsearch cluster's restClient is null for cluster:" + cluster));
    }

    @Override
    public void destroy() {
        if (!restClients.isEmpty()) {
            for (Map.Entry<String, RestHighLevelClient> entry : restClients.entrySet()) {
                try {
                    entry.getValue().close();
                    LOGGER.info("### Elasticsearch RestClient was destroyed! cluster=[{}].", entry.getKey());
                } catch (Exception e) {
                    LOGGER.error("### Elasticsearch RestClient destroyed error! cluster=[{}].", entry.getKey(), e);
                }
            }
        }
    }
}