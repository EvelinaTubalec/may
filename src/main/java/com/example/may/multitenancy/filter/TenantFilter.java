package com.example.may.multitenancy.filter;

import com.example.may.multitenancy.context.TenantContext;
import com.example.may.security.local.service.AuthenticationService;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Evelina Tubalets
 */
@Component
public class TenantFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {
        final String tenant = AuthenticationService.getTenant((HttpServletRequest) request);
        TenantContext.setCurrentTenant(tenant);
        /*
        if we will get tenant from header:
        final HttpServletRequest req = (HttpServletRequest) request;
        final String tenantName = req.getHeader("X-TenantID");
        TenantContext.setCurrentTenant(tenantName);
         */
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
