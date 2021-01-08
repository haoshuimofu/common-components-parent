package com.demo.components.elasticsearch.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Elasticsearch配置
 *
 * @author wude
 * @date 2021/1/8 16:31
 */
@Component
@ConfigurationProperties(prefix = "elasticsearch.environment")
public class ElasticsearchConfig implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private Map<String, ElasticsearchRestProperties> config;
    private Map<String, ElasticsearchRestClient> restClients = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("### --->>>Elasticsearch环境配置<<<---");
        logger.info(JSON.toJSONString(config, true));
        logger.info("### --->>>Elasticsearch环境配置<<<---");

        if (config != null && !config.isEmpty()) {
            for (Map.Entry<String, ElasticsearchRestProperties> entry : config.entrySet()) {
                restClients.put(entry.getKey(), new ElasticsearchRestClient(entry.getValue()));
                logger.info("### RestHighLevelClient instance init success! servers=[{}].", entry.getValue().getServers());
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (config != null && !config.isEmpty()) {
            for (Map.Entry<String, ElasticsearchRestClient> entry : restClients.entrySet()) {
                entry.getValue().destroy();
            }
        }
    }

    public Map<String, ElasticsearchRestClient> getRestClients() {
        return restClients;
    }

    public ElasticsearchRestClient getRestClient(String configName) {
        ElasticsearchRestClient client = restClients.get(configName);
        Assert.notNull(client, "Elasticsearch(" + configName + ") restClient 初始化异常!");
        return client;
    }

    public Map<String, ElasticsearchRestProperties> getConfig() {
        return config;
    }

    public void setConfig(Map<String, ElasticsearchRestProperties> config) {
        this.config = config;
    }

}