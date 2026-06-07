package com.ktn3.core_banking.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex) {

        try {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN);

            response.setContentType(
                    "application/json");

            new ObjectMapper()
                    .writeValue(
                            response.getOutputStream(),
                            Map.of(
                                    "code", 403,
                                    "message", "Access Denied"));

        } catch (Exception ignored) {

        }
    }
}
