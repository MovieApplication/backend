package com.movie.movieapi.service;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.repository.ReviewRepository;
import com.movie.movieapi.repository.UserRepository;
import com.movie.movieapi.util.MaskingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    @Transactional
    @CacheEvict(value = "review", allEntries = true)
    public void insertReview(ReviewInsertRequestDto reviewInsertRequestDto) {
        User user = userRepository.findByUserId(reviewInsertRequestDto.getUserId())
                .orElseThrow(null);
        reviewRepository.save(new Review(reviewInsertRequestDto, user));
    }

    @Transactional(readOnly = true)
    //@Cacheable(value = "review")
    public Page<ReviewSelectResponseDto> selectReviews(Long movieId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId,pageable);
        return reviews.map(review -> ReviewSelectResponseDto.builder()
                .review_id(review.get_id())
                .userId(MaskingUtil.maskName(review.getUser().getUserId()))
                .content(review.getContent())
                .build());

    }
}
