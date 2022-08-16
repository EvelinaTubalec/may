package com.example.may.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evelina Tubalets
 */
@Configuration
public class EmailConfig {

    @Bean
    @ConfigurationProperties(prefix = "mail")
    public EmailProperties emailProperties() {
        return new EmailProperties();
    }
}
