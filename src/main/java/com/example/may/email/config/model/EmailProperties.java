package com.example.may.email.config.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailProperties {

    @JsonProperty("mail.smtp.host")
    private String host;

    @JsonProperty("mail.smtp.port")
    private Integer port;

    @JsonProperty("mail.user")
    private String username;

    @JsonProperty("mail.password")
    private String password;

    @JsonProperty("mail.smtp.from")
    private String emailFrom;
}
