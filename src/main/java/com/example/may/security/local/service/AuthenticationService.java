package com.example.may.security.local.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

/**
 * @author Evelina Tubalets
 */
@Service
public class AuthenticationService {

    private static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
    private static final String SIGNINGKEY = "SecretKey";
    private static final String PREFIX = "Bearer";

    /**
     * Generates the JWT that contains tenant ID as an audience claim and
     * adds it to the Authorization header in the response.
     */
    public static void addToken(final HttpServletResponse res, final String username, final String tenant) {
        final String JwtToken = Jwts.builder()
                .setSubject(username)
                .setAudience(tenant)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
                .compact();
        res.addHeader("Authorization", PREFIX + " " + JwtToken);
    }

    public static Authentication getAuthentication(HttpServletRequest req) {
        final String token = req.getHeader("Authorization");
        if (token != null) {
            final String user = Jwts.parser()
                    .setSigningKey(SIGNINGKEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }

    /**
     Gets the tenant ID from the JWT .
     */
    public static String getTenant(HttpServletRequest req) {
        final String token = req.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        return Jwts.parser()
                .setSigningKey(SIGNINGKEY)
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody()
                .getAudience();
    }
}
