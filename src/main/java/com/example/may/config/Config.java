package com.example.may.config;

import com.example.may.email.EmailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Evelina Tubalets
 */
@Configuration
public class Config {

    @Bean
    @ConfigurationProperties(prefix = "mail")
    public EmailProperties emailProperties() {
        return new EmailProperties();
    }
}
