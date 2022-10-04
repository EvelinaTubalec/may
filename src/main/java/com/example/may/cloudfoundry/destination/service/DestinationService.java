package com.example.may.cloudfoundry.destination.service;

import com.example.may.cloudfoundry.destination.credentials.DestinationCredentials;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

import static com.example.may.core.mapper.JsonConverter.stringToModel;
import static com.example.may.core.ouath.constant.OAuth2Constants.BEARER_AUTHORIZATION_TYPE;
import static com.example.may.core.ouath.constant.OAuth2Constants.HEADER_NAME_AUTHORIZATION;
import static java.lang.String.format;

/**
 * returns properties from destination service.
 *
 * @author Evelina Tubalets
 */
@Slf4j
@Service
@AllArgsConstructor
public class DestinationService {

    public static final String NAME_OF_DESTINATION_CONFIGURATION_JSON_OBJECT = "destinationConfiguration";

    private final DestinationXsuaaTokenService destinationXsuaaTokenService;
    private final DestinationCredentials credentials;
    private final RestTemplate restTemplate;

    /**
     * uses token for getting properties from destination service.
     * will retry to call this method in case when token is invalid(expired).
     *
     * @return properties from destination service.
     */
    @Retryable(value = Unauthorized.class, maxAttempts = 2, backoff = @Backoff(0))
    public <T> T getProperties(final Class<T> type, final String destinationPath) {
        log.info("get token");
        try {
            final String accessToken = destinationXsuaaTokenService.getToken();
            final ResponseEntity<String> response = createRequestAndGetResponse(accessToken, destinationPath);
            return getPropertiesFromResponse(type, response);
        } catch (Unauthorized ex) {
            destinationXsuaaTokenService.refreshToken();
            throw ex;
        }
    }

    /**
     * creates url for destination service.
     *
     * @param destinationPath
     * @return resource service url.
     */
    public String getResourceServiceUrl(final String destinationPath) {
        return credentials.getUrl() + destinationPath;
    }

    /**
     * creates request to destination service and gets response from it.
     *
     * @param newTokenFromResponse which contains token from Authorization service of destination service.
     * @return response as ResponseEntity.
     */
    private ResponseEntity<String> createRequestAndGetResponse(
            final String newTokenFromResponse, final String destinationPath) {
        final HttpEntity<Void> newRequest = createRequestToResourceServer(newTokenFromResponse);
        return getResponseFromResourceServer(newRequest, destinationPath);
    }

    /**
     * creates request to destination service.
     *
     * @param accessToken which contains token from Authorization service of destination service.
     * @return request as HttpEntity.
     */
    private HttpEntity<Void> createRequestToResourceServer(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME_AUTHORIZATION.getValue(), format("%s %s", BEARER_AUTHORIZATION_TYPE.getValue(), accessToken));
        return new HttpEntity<>(headers);
    }

    /**
     * sends request to destination service via RestTemplate and gets response from it.
     *
     * @param request to destination service
     * @return response from destination service as ResponseEntity.
     */
    private ResponseEntity<String> getResponseFromResourceServer(
            final HttpEntity<Void> request, final String destinationPath) {
        final String resourceServiceUrl = getResourceServiceUrl(destinationPath);
        return restTemplate.exchange(resourceServiceUrl, HttpMethod.GET, request, String.class);
    }

    /**
     * gets needed properties as entity(map response to specific model)
     *
     * @param type     of Properties class which we want to get from destination service.
     * @param response from destination service.
     * @return entity with properties.
     */
    private <T> T getPropertiesFromResponse(final Class<T> type, final ResponseEntity<String> response) {
        final String responseBody = response.getBody();
        return stringToModel(type, responseBody, NAME_OF_DESTINATION_CONFIGURATION_JSON_OBJECT);
    }
}
