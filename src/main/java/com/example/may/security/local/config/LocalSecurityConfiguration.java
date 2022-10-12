package com.example.may.security.local.config;

import com.example.may.security.local.filter.AuthenticationFilter;
import com.example.may.security.local.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Evelina Tubalets
 */
@Profile("local")
@Configuration
public class LocalSecurityConfiguration  {

    @Bean
    public SecurityFilterChain localFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/info").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationManager(getAuthenticationManager(http))
                .addFilterBefore(new LoginFilter("/login" , getAuthenticationManager(http)),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
        return http.build();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
        final AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        final UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("admin")
                .build();
        final UserDetails tenant1 = User
                .withUsername("tenant1")
                .password(passwordEncoder().encode("password_tenant1"))
                .roles("tenant1")
                .build();
        final UserDetails tenant2 = User
                .withUsername("tenant2")
                .password(passwordEncoder().encode("password_tenant2"))
                .roles("tenant2")
                .build();
        return new InMemoryUserDetailsManager(user, tenant1, tenant2);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
