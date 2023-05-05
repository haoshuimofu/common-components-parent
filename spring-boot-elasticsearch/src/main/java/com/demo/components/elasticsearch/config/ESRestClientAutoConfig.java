package com.demo.components.elasticsearch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Elasticsearch RestClient autoConfig
 *
 * @author wude
 * @date 2021/4/15 11:42
 */
@Configuration
@EnableConfigurationProperties(value = {ESConfig.class})
public class ESRestClientAutoConfig {

    @Bean
    public ESRestClientContainer restClientContainer(ESConfig esConfig) throws Exception {
        return new ESRestClientContainer(esConfig);
    }

}