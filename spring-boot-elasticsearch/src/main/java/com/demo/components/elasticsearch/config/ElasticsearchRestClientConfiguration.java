package com.demo.components.elasticsearch.config;

import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wude
 * @date 2020/6/15 11:29
 */
@Configuration
@EnableConfigurationProperties(value = {ElasticsearchRestProperties.class})
public class ElasticsearchRestClientConfiguration {

    @Autowired
    private ElasticsearchRestProperties restProperties;

    @Bean
    public ElasticsearchRestClient elasticsearchRestClient() throws IOReactorException {
        return new ElasticsearchRestClient(restProperties);
    }

}