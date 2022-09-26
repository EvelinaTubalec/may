package com.example.may.email.config.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
public class EmailProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    @Value("${mail.emailFrom}")
    private String emailFrom;
}
