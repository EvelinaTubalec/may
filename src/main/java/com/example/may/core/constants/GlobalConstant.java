package com.example.may.core.constants;

/**
 * @author Evelina Tubalets
 */
public enum GlobalConstant {

    HEADER_NAME_AUTHORIZATION("Authorization"),
    BEARER_AUTHORIZATION_TYPE("Bearer"),
    CLIENT_ID_REQUEST_FIELD("client_id"),
    CLIENT_SECRET_REQUEST_FIELD("client_secret"),
    GRANT_TYPE_REQUEST_FIELD("grant_type"),
    ACCESS_TOKEN_RESPONSE_FIELD("access_token"),
    DESTINATION_CONFIGURATION_RESPONSE_FIELD("destinationConfiguration");

    final String value;

    GlobalConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
