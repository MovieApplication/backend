package com.movie.movieapi.dto;

import lombok.Data;

@Data
public class ProductionCompany {
    private Long id;
    private String logo_path;
    private String name;
    private String origin_country;
}
