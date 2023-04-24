package com.demo.components.desensitive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dewu.de
 * @date 2023-04-14 6:58 下午
 */
@Configuration
public class DesensitiveLogAutoConfiguration {

    @Bean
    public DesensitiveConfigContainer desensitiveConfigContainer() {
        return new DesensitiveConfigContainer();
    }
    
}
