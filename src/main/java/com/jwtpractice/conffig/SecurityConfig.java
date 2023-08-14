package com.jwtpractice.conffig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtpractice.security.JwtAuthenticationEntryPoint;
import com.jwtpractice.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    // Define the SecurityFilterChain bean to configure security filters
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        // Configure security settings
        security
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection
            .cors(cors -> cors.disable())  // Disable Cross-Origin Resource Sharing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/home/**").authenticated()  // URLs under /home require authentication
                .requestMatchers("/auth/login").permitAll()  // /auth/login is accessible without authentication
                .anyRequest().authenticated())  // All other requests require authentication
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))  // Set the authentication entry point
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // Set session policy to stateless

        // Add JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the SecurityFilterChain
        return security.build();
    }
}
