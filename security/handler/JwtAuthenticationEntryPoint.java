package com.ktn3.core_banking.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex) {

        try {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType(
                    "application/json");

            new ObjectMapper()
                    .writeValue(
                            response.getOutputStream(),
                            Map.of(
                                    "code", 401,
                                    "message", "Unauthorized"));

        } catch (Exception ignored) {

        }
    }
}
