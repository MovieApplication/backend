package com.movie.movieapi.dto;

import com.movie.movieapi.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSelectResponseDto implements Serializable {
    @Schema(example = "6446115f3e3b890047b35be4")
    private String reviewId;
    @Schema(example = "내용")
    private String content;
    @Schema(example = "1234567890")
    private String kakaoId;
    @Schema(example = "이름")
    private String userNickname;
    @Schema(example = "등록일자")
    private String regDatetime;

}
