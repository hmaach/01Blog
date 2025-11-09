package com.blog.shared.infrastructure.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.blog.shared.infrastructure.exception.UnauthorizedException;
import com.blog.shared.utils.JsonResponseWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthHandlers {

    public static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(
                HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException authException
        ) throws IOException {
            UnauthorizedException ex = new UnauthorizedException();

            JsonResponseWriter.write(
                    response,
                    ex.getStatus().value(),
                    ex.getCode(),
                    ex.getMessage()
            );
        }
    }
}
