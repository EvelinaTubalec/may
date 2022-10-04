package com.example.may.core.ouath.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Evelina Tubalets
 */
@Getter
@AllArgsConstructor
public enum OAuth2Constants {

    HEADER_NAME_AUTHORIZATION("Authorization"),
    BEARER_AUTHORIZATION_TYPE("Bearer"),
    CLIENT_ID_JSON_FIELD("client_id"),
    CLIENT_SECRET_JSON_FIELD("client_secret"),
    GRANT_TYPE_JSON_FIELD("grant_type"),
    ACCESS_TOKEN_JSON_FIELD("access_token");

    final String value;
}

