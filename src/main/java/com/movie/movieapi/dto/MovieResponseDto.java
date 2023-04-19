package com.movie.movieapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDto {
    private int page;
    private List<MovieContent> results;
    private int total_pages;
    private int total_results;
}
