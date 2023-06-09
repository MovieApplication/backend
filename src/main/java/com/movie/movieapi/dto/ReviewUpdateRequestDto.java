package com.movie.movieapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReviewUpdateRequestDto {
    @Schema(description = "리뷰ID",example = "1241")
    private String reviewId;
    @Schema(description = "내용",example = "리뷰내용")
    private String content;
}
