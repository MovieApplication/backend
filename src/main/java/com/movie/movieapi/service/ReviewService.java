package com.movie.movieapi.service;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.repository.ReviewRepository;
import com.movie.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    @Transactional
    @CacheEvict(value = "review", allEntries = true)
    public void insertReview(ReviewInsertRequestDto reviewInsertRequestDto) {
        User user = userRepository.findById(reviewInsertRequestDto.getUser_id())
                .orElseThrow(null);
        reviewRepository.save(new Review(reviewInsertRequestDto, user));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "review")
    public List<ReviewSelectResponseDto> selectReviews(String movieId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId,pageable);
        return reviews.stream().map(ReviewSelectResponseDto::new).collect(Collectors.toList());

    }
}
