package com.example.may.cloudfoundry.xsuaa.client;

import com.example.may.cloudfoundry.xsuaa.model.XsuaaClientCredentials;
import com.example.may.core.web.converter.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.may.core.ouath.constant.OAuth2Constants.ACCESS_TOKEN_JSON_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.CLIENT_ID_JSON_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.CLIENT_SECRET_JSON_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.GRANT_TYPE_JSON_FIELD;

/**
 * We need to get credentials from some specific service which is secured with OAuth2 security type using XSUAA service.
 * It means that we need to create request to Authorization service of this specific service and get token,
 * then we can use this token for getting credentials directly from specific service.
 * Our application in this case acts as a client.
 *
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class XsuaaOAuth2TokenClient {

    public static final String TOKEN_PATH = "/oauth/token";
    public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

    private final RestTemplate restTemplate;

    /**
     * gets token from service, use xsuaa client credentials for this.
     *
     * @param properties which contains clientId, clientSecret,
     *                   url for specific service and url for getting token(baseTokenUrl).
     * @return token as String.
     */
    public String getAccessToken(final XsuaaClientCredentials properties) {
        log.info("token");
        final HttpEntity<MultiValueMap<String, String>> request = buildRequest(properties);
        final ResponseEntity<String> response = sendRequest(request, properties);
        return getTokenFromResponse(response);
    }

    /**
     * creates request to Authorization service of specific service.
     *
     * @param properties which include client credentials.
     * @return request as HttpEntity.
     */
    private HttpEntity<MultiValueMap<String, String>> buildRequest(final XsuaaClientCredentials properties) {
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(CLIENT_ID_JSON_FIELD, properties.getClientId());
        requestBody.add(CLIENT_SECRET_JSON_FIELD, properties.getClientSecret());
        requestBody.add(GRANT_TYPE_JSON_FIELD, CLIENT_CREDENTIALS_GRANT_TYPE);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(requestBody, headers);
    }

    /**
     * sends request via RestTemplate and gets response from Authorization service of specific service.
     *
     * @param request    to Authorization service.
     * @param properties which include client credentials.
     * @return response as ResponseEntity.
     */
    private ResponseEntity<String> sendRequest(final HttpEntity<MultiValueMap<String, String>> request,
                                               final XsuaaClientCredentials properties) {
        final String tokenUrl = properties.getBaseTokenUrl() + TOKEN_PATH;
        return restTemplate.postForEntity(tokenUrl, request, String.class);
    }

    /**
     * parses response with token from Authorization service of specific service.
     *
     * @param response from Authorization service.
     * @return token as String.
     */
    private String getTokenFromResponse(final ResponseEntity<String> response) {
        final String responseBody = response.getBody();
        return JsonConverter.getValueFromJson(responseBody, ACCESS_TOKEN_JSON_FIELD);
    }
}
