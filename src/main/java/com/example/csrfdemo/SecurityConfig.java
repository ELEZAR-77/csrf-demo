package com.example.csrfdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable()); // ❌ CSRF выключен


//        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        // CSRF включён по умолчанию

        return http.build();
    }
}