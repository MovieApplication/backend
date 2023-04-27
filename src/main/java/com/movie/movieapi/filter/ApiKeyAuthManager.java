package com.movie.movieapi.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthManager implements AuthenticationManager {

    private final String API_KEY = "01057212058";



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        if (!API_KEY.equals(principal)) {
            throw new BadCredentialsException("API KEY가 권한이 없으므로 인증 불가 합니다.");
        }
        authentication.setAuthenticated(true);
        return authentication;
    }
}
