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
 * ES配置
 */
@Component
@ConfigurationProperties(prefix = "elasticsearch.config")
public class ElasticsearchConfig implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private Map<String, ElasticsearchProperties> environment;
    private Map<String, ElasticsearchClient> clients = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("### --->>>Elasticsearch环境配置<<<---");
        logger.info(JSON.toJSONString(environment, true));
        logger.info("### --->>>Elasticsearch环境配置<<<---");

        if (environment != null && !environment.isEmpty()) {
            for (Map.Entry<String, ElasticsearchProperties> entry : environment.entrySet()) {
                clients.put(entry.getKey(), new ElasticsearchClient(entry.getKey(), entry.getValue()));
                logger.info("### RestHighLevelClient instance init success! servers=[{}].", entry.getValue().getServers());
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (environment != null && !environment.isEmpty()) {
            for (Map.Entry<String, ElasticsearchClient> entry : clients.entrySet()) {
                entry.getValue().destroy();
            }
        }
    }

    public Map<String, ElasticsearchClient> getClients() {
        return clients;
    }

    public ElasticsearchClient getRestClient(String env) {
        ElasticsearchClient client = clients.get(env);
        Assert.notNull(client, "Elasticsearch(" + env + ") restClient 初始化异常!");
        return client;
    }

    public Map<String, ElasticsearchProperties> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, ElasticsearchProperties> environment) {
        this.environment = environment;
    }
}