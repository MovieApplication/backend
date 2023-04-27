package com.movie.movieapi.config;

import com.movie.movieapi.filter.ApiKeyAuthFilter;
import com.movie.movieapi.filter.ApiKeyAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Order(1)
@RequiredArgsConstructor
@Configuration
public class ApiSecurityConfig {

    private final ApiKeyAuthFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        filter.setAuthenticationManager(new ApiKeyAuthManager());
        http.cors()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(null)
                .accessDeniedHandler(null)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/**")
                .authenticated();
       return http.build();
    }
}
