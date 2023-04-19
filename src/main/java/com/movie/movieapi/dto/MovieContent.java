package com.movie.movieapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieContent {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private Long id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
}
