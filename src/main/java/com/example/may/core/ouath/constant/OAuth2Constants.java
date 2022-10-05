package com.example.may.core.ouath.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Evelina Tubalets
 */
@Getter
@AllArgsConstructor
public class OAuth2Constants {

    public static final String HEADER_NAME_AUTHORIZATION = "Authorization";
    public static final String BEARER_AUTHORIZATION_TYPE = "Bearer";
    public static final String CLIENT_ID_JSON_FIELD = "client_id";
    public static final String CLIENT_SECRET_JSON_FIELD = "client_secret";
    public static final String GRANT_TYPE_JSON_FIELD = "grant_type";
    public static final String ACCESS_TOKEN_JSON_FIELD = "access_token";
}

