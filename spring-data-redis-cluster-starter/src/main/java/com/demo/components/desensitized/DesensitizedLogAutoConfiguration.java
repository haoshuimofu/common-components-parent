package com.demo.components.desensitized;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dewu.de
 * @date 2023-04-14 6:58 下午
 */
@Configuration
public class DesensitizedLogAutoConfiguration {

    @Bean
    public DesensitizedConfigContainer desensitizedConfigContainer() {
        return new DesensitizedConfigContainer();
    }

    @Bean
    public DesensitizedLogHandler desensitizedLogHandler(DesensitizedConfigContainer configContainer) {
        return new DesensitizedLogHandler(configContainer);
    }

}
