package com.movie.movieapi.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final String API_KEY_AUTH_HEADER_NAME = "api-key-auth";

    @Autowired
    public ApiKeyAuthFilter(ApiKeyAuthManager manager) {
        this.setAuthenticationManager(manager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(API_KEY_AUTH_HEADER_NAME);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
