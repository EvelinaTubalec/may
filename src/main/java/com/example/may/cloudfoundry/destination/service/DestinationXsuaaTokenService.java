package com.example.may.cloudfoundry.destination.service;

import com.example.may.cloudfoundry.destination.credentials.DestinationCredentials;
import com.example.may.cloudfoundry.xsuaa.client.XsuaaOAuth2TokenClient;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.example.may.cloudfoundry.destination.cache.config.DestinationCacheConfiguration.DESTINATION_XSUAA_TOKENS_CACHE_MANAGER;
import static com.example.may.cloudfoundry.destination.cache.config.DestinationCacheConfiguration.DESTINATION_XSUAA_TOKENS_CACHE_NAME;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class DestinationXsuaaTokenService {

    private final XsuaaOAuth2TokenClient client;
    private final DestinationCredentials credentials;

    @Cacheable(cacheManager = DESTINATION_XSUAA_TOKENS_CACHE_MANAGER, cacheNames = DESTINATION_XSUAA_TOKENS_CACHE_NAME)
    public String getToken() {
        return client.getAccessToken(credentials);
    }

    @CachePut(cacheManager = DESTINATION_XSUAA_TOKENS_CACHE_MANAGER, cacheNames = DESTINATION_XSUAA_TOKENS_CACHE_NAME)
    public String refreshToken() {
        return getToken();
    }
}
