package com.example.may.cloudfoundry.xsuaa.model;

import lombok.Data;

/**
 * @author Evelina Tubalets
 */
@Data
public abstract class XsuaaClientCredentials {

    private String baseTokenUrl;

    private String clientId;

    private String clientSecret;

    private String url;
}
