package com.demo.components.shardingjdbc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author wude
 * @datetime 2021-08-20 下午5:09
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "database")
public class DatabaseConfig {

    private Map<String, DruidProperty> config;

}
