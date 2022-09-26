package com.example.may.cloudfoundry.xsuaa.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
@ConfigurationProperties(prefix = "destination-service")
public class XsuaaClientCredentials {

    private String baseTokenUrl;

    private String clientId;

    private String clientSecret;

    private String url;
}
