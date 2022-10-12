package com.example.may.multitenancy.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Evelina Tubalets
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantContext {

    /*
    Each of threads will use his own value of CURRENT_TENANT. Own value of CURRENT_TENANT for each request=thread.
     */
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(final String tenant) {
        CURRENT_TENANT.set(tenant);
    }
}
