package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewInsertRequestDto {
    @Schema(example = "사용자 이름")
    private String userId;
    @Schema(example = "603692")
    private String movieId;
    @Schema(example = "리뷰 내용작성해주세요.")
    private String content;
}
