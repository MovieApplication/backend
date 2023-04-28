package com.movie.movieapi.service;

import com.movie.movieapi.config.WebClientConfig;
import com.movie.movieapi.dto.MovieDetailResponseDto;
import com.movie.movieapi.dto.MovieResponseDto;
import com.movie.movieapi.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    @Value("${api_key}")
    private String apiKey;
    @Value("${image-url}")
    private String defaultImageUrl;

    private final WebClientConfig webClient;


    public MovieResponseDto selectMoviesInTheater(Integer page) {
        MovieResponseDto movieList = webClient.webClientInMovieSearch().get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .queryParam("page", page)
                        .queryParam("primary_release_date.gte", LocalDateTime.now().minusMonths(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .queryParam("primary_release_date.lte", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .retrieve().bodyToMono(MovieResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movieList)) {
            movieList.getResults().forEach(movie -> {
                        movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                        movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                    }
            );
        }
        return movieList;
    }

    public MovieResponseDto selectPopularMovies(Integer page) {
        MovieResponseDto movieList = webClient.webClientInMovie().get()
                .uri(uriBuilder -> uriBuilder.path("/popular")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .queryParam("page", page)
                        .build())
                .retrieve().bodyToMono(MovieResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movieList)) {
            movieList.getResults().forEach(movie -> {
                        movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                        movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                    }
            );
        }
        return movieList;
    }

    public MovieDetailResponseDto selectMovie(Long movieId) {
        MovieDetailResponseDto movie = webClient.webClientInMovie().get()
                .uri(uriBuilder -> uriBuilder.path("/" + movieId)
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .build())
                .retrieve().bodyToMono(MovieDetailResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movie)) {
            movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
            movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
            movie.getProduction_companies().forEach(company -> company.setLogo_path(defaultImageUrl + company.getLogo_path()));
        }
        return movie;
    }

    public MovieResponseDto selectSimilarMovie(Long movieId,Integer page) {
        MovieResponseDto movieList = webClient.webClientInMovie().get()
                .uri(uriBuilder -> uriBuilder.path("/" + movieId + "/similar")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .queryParam("page", page)
                        .build())
                .retrieve().bodyToMono(MovieResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movieList)) {
            movieList.getResults().forEach(movie -> {
                        movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                        movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                    }
            );
        }
        return movieList;
    }

    public MovieResponseDto selectVoteAverageMovies(Integer page) {
            MovieResponseDto movieList = webClient.webClientInMovieSearch().get()
                    .uri(uriBuilder -> uriBuilder.path("")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "ko")
                            .queryParam("sort_by","vote_average.desc")
                            .queryParam("page",page)
                            .build())
                    .retrieve().bodyToMono(MovieResponseDto.class)
                    .block();
            if (!ObjectUtils.isEmpty(movieList)) {
                movieList.getResults().forEach(movie -> {
                            movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                            movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                        }
                );
            }
            return movieList;
    }

    public MovieResponseDto selectReleaseDateMovies(Integer page) {
        MovieResponseDto movieList = webClient.webClientInMovieSearch().get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .queryParam("sort_by", "primary_release_date.desc")
                        .queryParam("page", page)
                        .queryParam("primary_release_date.lte", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build())
                .retrieve().bodyToMono(MovieResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movieList)) {
            movieList.getResults().forEach(movie -> {
                        movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                        movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                    }
            );
        }
        return movieList;
    }

    public MovieResponseDto selectYearMovies(SearchDto searchDto) {
        //년도별 영화 조회 시
        MovieResponseDto movieList = webClient.webClientInMovieSearch().get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko")
                        .queryParam("primary_release_year",searchDto.getYear())
                        .queryParam("page",searchDto.getPage())
                        .build())
                .retrieve().bodyToMono(MovieResponseDto.class)
                .block();
        if (!ObjectUtils.isEmpty(movieList)) {
            movieList.getResults().forEach(movie -> {
                        movie.setPoster_path(defaultImageUrl + movie.getPoster_path());
                        movie.setBackdrop_path(defaultImageUrl + movie.getBackdrop_path());
                    }
            );
        }
        return movieList;
    }
}

