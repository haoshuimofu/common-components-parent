package com.demo.components.elasticsearch.config;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Elasticsearch Rest Client container for multi-env
 *
 * @author wude
 * @date 2021/4/15 11:47
 */
public class ESRestClientContainer implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESRestClientContainer.class);

    private ESConfig esConfig;
    private Map<String, ESRestClient> restClients = new HashMap<>();

    public ESRestClientContainer(ESConfig esConfig) throws Exception {
        this.esConfig = esConfig;

        LOGGER.info("### --->>> Elasticsearch config <<<---");
        LOGGER.info(JSON.toJSONString(esConfig, true));
        LOGGER.info("### --->>> Elasticsearch config <<<---");

        if (esConfig.getEnvironment() != null && !esConfig.getEnvironment().isEmpty()) {
            for (Map.Entry<String, ESRestProperties> entry : esConfig.getEnvironment().entrySet()) {
                restClients.put(entry.getKey(), new ESRestClient(entry.getKey(), entry.getValue()));
            }
        }
    }

    public RestHighLevelClient restHighLevelClient(String env) {
        Assert.notNull(env, "Elasticsearch env is null.");
        ESRestClient restClient = restClients.get(env);
        Assert.notNull(restClient, "Elasticsearch RestClient is null for env = " + env + "!");
        return restClient.getRestClient();
    }

    public ESConfig getEsConfig() {
        return esConfig;
    }

    @Override
    public void destroy() throws Exception {
        if (esConfig.getEnvironment() != null && !esConfig.getEnvironment().isEmpty()) {
            for (Map.Entry<String, ESRestClient> entry : restClients.entrySet()) {
                entry.getValue().destroy();
                LOGGER.info("### Elasticsearch restClient was destroyed! env=[{}].", entry.getKey());
            }
        }
    }
}