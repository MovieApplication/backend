package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDto {
    @Schema(example = "페이지")
    private int page;
    @Schema(example = "결과값")
    private List<MovieContent> results;
    @Schema(example = "총페이지 수")
    private int total_pages;
    @Schema(example = "총결과 수")
    private int total_results;
}
