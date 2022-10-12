package com.example.may.security.local.filter;

import com.example.may.security.local.model.AuthenticationCredentials;
import com.example.may.security.local.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Evelina Tubalets
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(final String url, final AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }
    /**
     Intercepts a request and attempts to perform authentication. In this method,
     we map the user credentials to the AccountCredentials DTO class and authenticate the user
     against the in-memory authentication manager.
     */
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
            throws AuthenticationException, IOException{
        final AuthenticationCredentials credentials = new ObjectMapper()
                .readValue(req.getInputStream(), AuthenticationCredentials.class);
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()));
    }

    /*
    Generate the JWT and add the tenant ID.
     */
    @Override
    protected void successfulAuthentication(
            final HttpServletRequest req, final HttpServletResponse res,
            final FilterChain chain, final Authentication auth){

        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String tenant = "";
        for (GrantedAuthority gauth : authorities) {
            tenant = gauth.getAuthority();
        }
        AuthenticationService.addToken(res, auth.getName(), tenant.substring(5));
    }
}