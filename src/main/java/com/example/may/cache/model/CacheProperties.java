package com.example.may.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author Evelina Tubalets
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CacheProperties {

    private int expiresAfter;

    private TimeUnit timeUnit;
}
