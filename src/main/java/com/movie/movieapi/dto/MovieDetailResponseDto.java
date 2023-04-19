package com.movie.movieapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetailResponseDto {
    private boolean adult;
    private String backdrop_path;
    //private String belongs_to_collection;
    private Long budget;
    private List<Genre> genres;
    private String homepage;
    private Long id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private String release_date;
    private Long revenue;
    private int runtime;
    private List<SpokenLanguages> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;


}
