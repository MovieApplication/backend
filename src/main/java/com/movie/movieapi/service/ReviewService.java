package com.movie.movieapi.service;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.dto.ReviewUpdateRequestDto;
import com.movie.movieapi.exception.CommonErrorCode;
import com.movie.movieapi.exception.RestApiException;
import com.movie.movieapi.exception.UserErrorCode;
import com.movie.movieapi.repository.ReviewRepository;
import com.movie.movieapi.repository.UserRepository;
import com.movie.movieapi.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    @Transactional
    @CacheEvict(value = "review", allEntries = true)
    public void insertReview(ReviewInsertRequestDto reviewInsertRequestDto, User user) {
        User userInfo = userRepository.findByKakaoId(user.getKakaoId())
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));

        boolean review = reviewRepository.existsByUserAndMovieIdAndContentAndDelYn(userInfo,reviewInsertRequestDto.getMovieId(),reviewInsertRequestDto.getContent(),false);

        if(review){
            throw new RestApiException(CommonErrorCode.NOT_DUPLICATION_REVIEW);
        }
        reviewRepository.save(new Review(reviewInsertRequestDto, userInfo));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "review")
    public Page<ReviewSelectResponseDto> selectReviews(Long movieId, Pageable pageable) {

        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId,pageable);
        return reviews.map(review -> ReviewSelectResponseDto.builder()
                .reviewId(review.get_id())
                .kakaoId(review.getUser().getKakaoId())
                .userNickname(review.getUser().getUserNickname())
                .content(review.getContent())
                .delYn(review.isDelYn())
                .regDatetime(DateUtils.convertMongoDate(String.valueOf(review.getRegDatetime())))
                .build());

    }

    @CacheEvict(value = "review", allEntries = true)
    @Transactional
    public void updateReview(ReviewUpdateRequestDto requestDto,User user) {
        Review review = reviewRepository.findById(requestDto.getReviewId())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_REVIEW));
        //본인 글인지 판단
        if(review.getUser().getKakaoId().equals(user.getKakaoId())){
            //리뷰 수정
            review.updateReview(requestDto);
            //DB 저장
            reviewRepository.save(review);
        }else{
            throw new RestApiException(CommonErrorCode.NOT_MINE_REVIEW);
        }
    }


    @CacheEvict(value = "review", allEntries = true)
    @Transactional
    public void deleteReview(String reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.NOT_FOUND_REVIEW));
        //본인 글인지 판단
        if(review.getUser().getKakaoId().equals(user.getKakaoId())){
            //리뷰 삭제
            review.deleteReview();
            //DB 저장
            reviewRepository.save(review);
        }else{
            throw new RestApiException(CommonErrorCode.NOT_MINE_REVIEW);
        }
    }
}
