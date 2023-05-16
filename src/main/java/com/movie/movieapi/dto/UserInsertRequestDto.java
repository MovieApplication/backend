package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInsertRequestDto {
    @Schema(example = "1234567890")
    private String kakaoId;
    @Schema(example = "김영재")
    private String userNickname;
}
