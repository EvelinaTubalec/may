package com.example.may.cloudfoundry.xsuaa.client;

import com.example.may.cloudfoundry.xsuaa.model.XsuaaClientCredentials;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.may.core.ouath.constant.OAuth2Constants.ACCESS_TOKEN_RESPONSE_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.CLIENT_ID_REQUEST_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.CLIENT_SECRET_REQUEST_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.GRANT_TYPE_REQUEST_FIELD;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class XsuaaOAuth2TokenClient {

    public static final String TOKEN_PATH = "/oauth/token";
    public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

    private final RestTemplate restTemplate;

    public String getAccessToken(final XsuaaClientCredentials properties) {
        log.info("token");
        final HttpEntity<MultiValueMap<String, String>> request = createRequestForGettingToken(properties);
        final ResponseEntity<String> response = getResponseForTokenRequest(request, properties);
        return getTokenFromResponse(response);
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestForGettingToken(final XsuaaClientCredentials properties) {
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(CLIENT_ID_REQUEST_FIELD.getValue(), properties.getClientId());
        requestBody.add(CLIENT_SECRET_REQUEST_FIELD.getValue(), properties.getClientSecret());
        requestBody.add(GRANT_TYPE_REQUEST_FIELD.getValue(), CLIENT_CREDENTIALS_GRANT_TYPE);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(requestBody, headers);
    }

    private ResponseEntity<String> getResponseForTokenRequest(final HttpEntity<MultiValueMap<String, String>> request,
                                                              final XsuaaClientCredentials properties) {
        final String tokenUrl = properties.getBaseTokenUrl() + TOKEN_PATH;
        return restTemplate.postForEntity(tokenUrl, request, String.class);
    }

    private String getTokenFromResponse(final ResponseEntity<String> response) {
        final String responseBody = response.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        return obj.get(ACCESS_TOKEN_RESPONSE_FIELD.getValue()).toString();
    }
}
