package com.demo.components.elasticsearch.config;

import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2020/6/15 11:29
 */
@Configuration
//@EnableConfigurationProperties(value = {ElasticsearchRestProperties.class})
public class ElasticsearchRestClientConfiguration {

//    private final ElasticsearchRestProperties restProperties;
//
//    public ElasticsearchRestClientConfiguration(ElasticsearchRestProperties restProperties) {
//        this.restProperties = restProperties;
//    }

    /**
     * elasticsearch.rest.servers如果存在此配置则单独注册一个RestHighLevelClient实例
     *
     * @return
     */
    @ConditionalOnProperty(prefix = "elasticsearch.rest", name = "servers")
    @Bean
    public ElasticsearchRestProperties getRestProperties() {
        return new ElasticsearchRestProperties();
    }

    @ConditionalOnBean(ElasticsearchRestProperties.class)
    @Bean
    public ElasticsearchRestClient elasticsearchRestClient() throws IOReactorException {
        return new ElasticsearchRestClient(getRestProperties());
    }

}