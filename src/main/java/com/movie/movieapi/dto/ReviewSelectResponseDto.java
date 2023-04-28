package com.movie.movieapi.dto;

import com.movie.movieapi.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSelectResponseDto implements Serializable {
    @Schema(example = "6446115f3e3b890047b35be4")
    private String userId;
    @Schema(example = "내용")
    private String content;

    public ReviewSelectResponseDto(Review review) {
        this.userId = review.getUser().getUserId();
        this.content = review.getContent();
    }
}
