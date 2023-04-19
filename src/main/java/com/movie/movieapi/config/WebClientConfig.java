package com.movie.movieapi.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



@Component
public class WebClientConfig {

    public WebClient webClientInMovie(){
        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/movie")
                .defaultCookie("key","value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public WebClient webClientInMovieSearch(){
        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/discover/movie")
                .defaultCookie("key","value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
