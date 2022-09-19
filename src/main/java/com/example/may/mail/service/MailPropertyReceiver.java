package com.example.may.mail.service;

import com.example.may.mail.config.model.MailDestinationTokenProperties;
import com.example.may.mail.config.model.MailProperties;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.example.may.core.constants.GlobalConstant.ACCESS_TOKEN_RESPONSE_FIELD;
import static com.example.may.core.constants.GlobalConstant.BEARER_AUTHORIZATION_TYPE;
import static com.example.may.core.constants.GlobalConstant.CLIENT_ID_REQUEST_FIELD;
import static com.example.may.core.constants.GlobalConstant.CLIENT_SECRET_REQUEST_FIELD;
import static com.example.may.core.constants.GlobalConstant.DESTINATION_CONFIGURATION_RESPONSE_FIELD;
import static com.example.may.core.constants.GlobalConstant.GRANT_TYPE_REQUEST_FIELD;
import static com.example.may.core.constants.GlobalConstant.HEADER_NAME_AUTHORIZATION;
import static com.example.may.mail.config.model.MailConstant.MAIL_FROM_RESPONSE_FIELD;
import static com.example.may.mail.config.model.MailConstant.MAIL_HOST_RESPONSE_FIELD;
import static com.example.may.mail.config.model.MailConstant.MAIL_PASSWORD_RESPONSE_FIELD;
import static com.example.may.mail.config.model.MailConstant.MAIL_PORT_RESPONSE_FIELD;
import static com.example.may.mail.config.model.MailConstant.MAIL_USER_RESPONSE_FIELD;
import static java.lang.String.format;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class MailPropertyReceiver {

    public static final String TOKEN_PATH = "/oauth/token";
    public static final String MAIL_DESTINATION_PATH = "/destination-configuration/v1/destinations/mail-destination";
    public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

    private final MailDestinationTokenProperties properties;
    private final RestTemplate restTemplate;

    public MailProperties getEmailPropertiesFromDestinationService() {
        final String accessToken = getAccessToken();
        return getEmailProperties(accessToken);
    }

    private String getAccessToken() {
        final HttpEntity<MultiValueMap<String, String>> request = createRequestForGettingToken();
        final ResponseEntity<String> response = getResponseForTokenRequest(request);
        return getTokenFromResponse(response);
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestForGettingToken() {
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(CLIENT_ID_REQUEST_FIELD.getValue(), properties.getClientId());
        requestBody.add(CLIENT_SECRET_REQUEST_FIELD.getValue(), properties.getClientSecret());
        requestBody.add(GRANT_TYPE_REQUEST_FIELD.getValue(), CLIENT_CREDENTIALS_GRANT_TYPE);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<>(requestBody, headers);
    }

    private ResponseEntity<String> getResponseForTokenRequest(final HttpEntity<MultiValueMap<String, String>> request) {
        final String tokenUrl = properties.getBaseTokenUrl() + TOKEN_PATH;
        return restTemplate.postForEntity(tokenUrl, request, String.class);
    }

    private String getTokenFromResponse(final ResponseEntity<String> response) {
        final String responseBody = response.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        return obj.get(ACCESS_TOKEN_RESPONSE_FIELD.getValue()).toString();
    }

    private MailProperties getEmailProperties(final String accessToken) {
        final HttpEntity<Void> request = createRequestToResourceServer(accessToken);
        final ResponseEntity<String> response = getResponseFromResourceServer(request);
        return getEmailPropertiesFromResponse(response);
    }

    private HttpEntity<Void> createRequestToResourceServer(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME_AUTHORIZATION.getValue(), format("%s %s", BEARER_AUTHORIZATION_TYPE.getValue(), accessToken));
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> getResponseFromResourceServer(final HttpEntity<Void> request) {
        final String resourceServiceUrl = properties.getUrl() + MAIL_DESTINATION_PATH;
        return restTemplate.exchange(resourceServiceUrl, HttpMethod.GET, request, String.class);
    }

    private MailProperties getEmailPropertiesFromResponse(final ResponseEntity<String> response) {
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

        final MailProperties mailProperties = new MailProperties();
        mailProperties.setHost(host);
        mailProperties.setPort(Integer.parseInt(port));
        mailProperties.setUsername(username);
        mailProperties.setPassword(password);
        mailProperties.setEmailFrom(from);
        return mailProperties;
    }
}
