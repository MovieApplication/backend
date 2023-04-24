package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInsertRequestDto {
    @Schema(example = "6446115f3e3b890047b35be4")
    private String userId;
}
