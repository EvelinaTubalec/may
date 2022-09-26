package com.example.may.cloudfoundry.destination.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * @author Evelina Tubalets
 */
@Configuration
public class DestinationCacheConfiguration {

    public static final String DESTINATION_XSUAA_TOKENS_CACHE_NAME = "token";
    public static final String DESTINATION_XSUAA_TOKENS_CACHE_MANAGER = "destinationXsuaaTokensCacheManager";

    private static final long EXPIRATION_TIME = 20;
    private static final TimeUnit EXPIRATION_TIME_UNIT = MINUTES;

    @Bean
    public CacheManager destinationXsuaaTokensCacheManager(final Caffeine<Object, Object> destinationXsuaaTokensCaffeine) {
        final CaffeineCacheManager tokensCacheManager = new CaffeineCacheManager();
        tokensCacheManager.setCaffeine(destinationXsuaaTokensCaffeine);
        return tokensCacheManager;
    }

    @Bean
    public Caffeine<Object, Object> destinationXsuaaTokensCaffeine() {
        return Caffeine.newBuilder().expireAfterWrite(EXPIRATION_TIME, EXPIRATION_TIME_UNIT);
    }
}
