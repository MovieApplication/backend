package com.movie.movieapi.service;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.repository.ReviewRepository;
import com.movie.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    @Transactional
    public void insertReview(ReviewInsertRequestDto reviewInsertRequestDto) {
        User user = userRepository.findById(reviewInsertRequestDto.getUserId())
                .orElseThrow(null);
        reviewRepository.save(new Review(reviewInsertRequestDto, user));
    }

    @Transactional(readOnly = true)
    public Stream<ReviewSelectResponseDto> selectReviews(String movieId) {
        List<Review> reviews = reviewRepository.findAllByMovieId(movieId);
        return reviews.stream().map(ReviewSelectResponseDto::new);
    }
}
