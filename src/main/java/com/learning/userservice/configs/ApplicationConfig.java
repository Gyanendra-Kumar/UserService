package com.learning.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfig {
    @Bean  // Created a bean of BCryptPasswordEncoder, so we don't need to re-initialize the class everytime user sign-up
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

    httpSecurity
            // 1. Disable CSRF if you are building a stateless REST API (like with JWTs)
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            // 2. Configure endpoint authorization
            .authorizeHttpRequests(
        authorize -> authorize
                // Allow anyone to access /signup and /login
                .requestMatchers("/users/signup", "/users/login", "/users/validate/*",
                        // Swagger UI
                        "/swagger-ui.html",
                        "/swagger-ui/**",

                        // OpenAPI JSON
                        "/v3/api-docs",
                        "/v3/api-docs/**")
                .permitAll()
                // All other requests require the user to be authenticated
                .anyRequest().authenticated());

    return httpSecurity.build();
    };
}
