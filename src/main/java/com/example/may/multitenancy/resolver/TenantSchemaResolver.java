package com.example.may.multitenancy.resolver;

import com.example.may.multitenancy.context.TenantContext;
import lombok.AllArgsConstructor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNullElse;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    public static final String DEFAULT_TENANT = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        final String currentTenant = TenantContext.getCurrentTenant();
        return requireNonNullElse(currentTenant, DEFAULT_TENANT);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
