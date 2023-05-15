package com.movie.movieapi.controller;

import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.dto.ReviewUpdateRequestDto;
import com.movie.movieapi.result.ListResponsePage;
import com.movie.movieapi.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "리뷰를 등록합니다.",security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<?> insertReview(@RequestBody ReviewInsertRequestDto reviewInsertRequestDto,
                                          @AuthenticationPrincipal User user) {
        reviewService.insertReview(reviewInsertRequestDto,user);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.",security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId")String reviewId,
                                          @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto,
                                          @AuthenticationPrincipal User user) {
        reviewService.updateReview(reviewId,reviewUpdateRequestDto,user);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.",security = @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId")String reviewId,
                                          @AuthenticationPrincipal User user){
        reviewService.deleteReview(reviewId,user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "리뷰 목록 조회", description = "리뷰 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 처리", content = @Content(schema = @Schema(implementation = ReviewSelectResponseDto.class)))
    })
    @GetMapping("/{movieId}")
    public ResponseEntity<ListResponsePage<ReviewSelectResponseDto>> selectReviews(@PathVariable("movieId") Long movieId,
                                                                                   Pageable pageable) {
        return ResponseEntity.ok(new ListResponsePage<>(reviewService.selectReviews(movieId,pageable)));
    }
}
