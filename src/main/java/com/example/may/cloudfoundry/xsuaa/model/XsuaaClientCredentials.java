package com.example.may.cloudfoundry.xsuaa.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Evelina Tubalets
 */
@Data
@Component
public abstract class XsuaaClientCredentials {

    private String baseTokenUrl;

    private String clientId;

    private String clientSecret;

    private String url;
}
