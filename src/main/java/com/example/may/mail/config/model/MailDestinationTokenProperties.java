package com.example.may.mail.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
@ConfigurationProperties("destination-service")
public class MailDestinationTokenProperties {

    private String baseTokenUrl;

    private String clientId;

    private String clientSecret;

    private String url;
}
