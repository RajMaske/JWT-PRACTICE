package com.jwtpractice.conffig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

    // Define a UserDetailsService bean
    @Bean
    public UserDetailsService userService() {
        // Create UserDetails objects representing users
        UserDetails user = User.builder().username("raj").password(encoder().encode("raj")).roles("ADMIN").build();
        UserDetails user1 = User.builder().username("Abc").password(encoder().encode("abc")).roles("ADMIN").build();
        
        // Return an InMemoryUserDetailsManager with the defined users
        return new InMemoryUserDetailsManager(user, user1);
    }

    // Define a PasswordEncoder bean for password hashing
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // Define an AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
