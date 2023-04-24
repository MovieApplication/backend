package com.movie.movieapi.controller;

import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.")
    @PostMapping("")
    public ResponseEntity<?> insertReview(@RequestBody ReviewInsertRequestDto reviewInsertRequestDto) {
        reviewService.insertReview(reviewInsertRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리뷰 목록 조회", description = "리뷰 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리", content = @Content(schema = @Schema(implementation = ReviewSelectResponseDto.class)))
    })
    @GetMapping("/{movieId}")
    public ResponseEntity<Stream<ReviewSelectResponseDto>> selectReviews(@PathVariable("movieId") String movieId) {
        return ResponseEntity.ok(reviewService.selectReviews(movieId));

    }
}
