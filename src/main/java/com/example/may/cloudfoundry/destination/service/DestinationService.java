package com.example.may.cloudfoundry.destination.service;

import com.example.may.email.config.model.EmailProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

import static com.example.may.core.ouath.constant.OAuth2Constants.BEARER_AUTHORIZATION_TYPE;
import static com.example.may.core.ouath.constant.OAuth2Constants.DESTINATION_CONFIGURATION_RESPONSE_FIELD;
import static com.example.may.core.ouath.constant.OAuth2Constants.HEADER_NAME_AUTHORIZATION;
import static com.example.may.email.config.model.EmailConstant.MAIL_FROM_RESPONSE_FIELD;
import static com.example.may.email.config.model.EmailConstant.MAIL_HOST_RESPONSE_FIELD;
import static com.example.may.email.config.model.EmailConstant.MAIL_PASSWORD_RESPONSE_FIELD;
import static com.example.may.email.config.model.EmailConstant.MAIL_PORT_RESPONSE_FIELD;
import static com.example.may.email.config.model.EmailConstant.MAIL_USER_RESPONSE_FIELD;
import static java.lang.String.format;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class DestinationService {

    public static final String MAIL_DESTINATION_PATH = "/destination-configuration/v1/destinations/mail-destination";

    private final DestinationXsuaaTokenService destinationXsuaaTokenService;
    private final RestTemplate restTemplate;

    @Retryable(value = Unauthorized.class, maxAttempts = 2, backoff = @Backoff(0))
    public EmailProperties getEmailProperties() {
        log.info("get token");
        try {
            final String accessToken = destinationXsuaaTokenService.getToken();
            final ResponseEntity<String> response = createRequestAndGetResponse(accessToken);
            return getEmailPropertiesFromResponse(response);
        } catch (Unauthorized ex) {
            destinationXsuaaTokenService.refreshToken();
            throw ex;
        }
    }

    private ResponseEntity<String> createRequestAndGetResponse(final String newTokenFromResponse) {
        final HttpEntity<Void> newRequest = createRequestToResourceServer(newTokenFromResponse);
        return getResponseFromResourceServer(newRequest);
    }

    private HttpEntity<Void> createRequestToResourceServer(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME_AUTHORIZATION.getValue(), format("%s %s", BEARER_AUTHORIZATION_TYPE.getValue(), accessToken));
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> getResponseFromResourceServer(final HttpEntity<Void> request) {
        final String resourceServiceUrl = destinationXsuaaTokenService.getResourceServiceUrl();
        return restTemplate.exchange(resourceServiceUrl, HttpMethod.GET, request, String.class);
    }

    private EmailProperties getEmailPropertiesFromResponse(final ResponseEntity<String> response) {
        final String body = response.getBody();
        final JSONObject obj = new JSONObject(body);
        final String port = obj
                .getJSONObject(DESTINATION_CONFIGURATION_RESPONSE_FIELD.getValue())
                .getString(MAIL_PORT_RESPONSE_FIELD.getValue());
        final String username = obj
                .getJSONObject(DESTINATION_CONFIGURATION_RESPONSE_FIELD.getValue())
                .getString(MAIL_USER_RESPONSE_FIELD.getValue());
        final String password = obj
                .getJSONObject(DESTINATION_CONFIGURATION_RESPONSE_FIELD.getValue())
                .getString(MAIL_PASSWORD_RESPONSE_FIELD.getValue());
        final String from = obj
                .getJSONObject(DESTINATION_CONFIGURATION_RESPONSE_FIELD.getValue())
                .getString(MAIL_FROM_RESPONSE_FIELD.getValue());
        final String host = obj
                .getJSONObject(DESTINATION_CONFIGURATION_RESPONSE_FIELD.getValue())
                .getString(MAIL_HOST_RESPONSE_FIELD.getValue());

        final EmailProperties emailProperties = new EmailProperties();
        emailProperties.setHost(host);
        emailProperties.setPort(Integer.parseInt(port));
        emailProperties.setUsername(username);
        emailProperties.setPassword(password);
        emailProperties.setEmailFrom(from);
        return emailProperties;
    }
}
