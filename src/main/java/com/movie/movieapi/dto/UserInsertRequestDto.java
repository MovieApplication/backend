package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInsertRequestDto {
    @Schema(example = "newy12@naver.com")
    private String userId;
}
