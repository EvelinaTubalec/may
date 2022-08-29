package com.example.may.email.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class EmailProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String emailFrom;
}
