package com.ktn3.core_banking.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ktn3.core_banking.security.filter.JwtAuthenticationFilter;
import com.ktn3.core_banking.security.handler.JwtAccessDeniedHandler;
import com.ktn3.core_banking.security.handler.JwtAuthenticationEntryPoint;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private final JwtAuthenticationEntryPoint entryPoint;

    private final JwtAccessDeniedHandler deniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception ->

                        exception
                                .authenticationEntryPoint(
                                        entryPoint)
                                .accessDeniedHandler(
                                        deniedHandler))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**")
                        .permitAll()

                        .requestMatchers(
                                "/api/v1/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/api/v1/checker/**")
                        .hasRole("CHECKER")

                        .requestMatchers(
                                "/api/v1/approver/**")
                        .hasRole("APPROVER")

                        .requestMatchers(
                                "/api/v1/maker/**")
                        .hasRole("MAKER")

                        .anyRequest()
                        .authenticated())

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @PostConstruct
    public void init() {
        System.out.println(">>> SECURITY CONFIG LOADED <<<");
    }
}