package com.example.may.cache.config;

import com.example.may.cache.model.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * @author Evelina Tubalets
 */
@EnableCaching
@Configuration
public class CacheConfig {

    public static final String DEFAULT_CACHE_NAME = "default";
    public static final String DEFAULT_CACHE_MANAGER_NAME = "defaultCacheManager";

    @Bean
    public Caffeine<Object, Object> defaultCaffeineConfig(final CacheProperties cacheProperties) {
        final int expiresAfter = cacheProperties.getExpiresAfter();
        final TimeUnit timeUnit = cacheProperties.getTimeUnit();
        return Caffeine.newBuilder().expireAfterWrite(expiresAfter, timeUnit);
    }

    @Bean
    @Primary
    public CacheManager defaultCacheManager(@Qualifier("defaultCaffeineConfig") final Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @Bean
    public CacheProperties cacheProperties(
            @Value("${cache.properties.expires-after}") final int expiresAfter,
            @Value("${cache.properties.time-unit}") final String timeUnit) {
        final TimeUnit unit = TimeUnit.valueOf(timeUnit);
        return new CacheProperties(expiresAfter, unit);
    }
}
