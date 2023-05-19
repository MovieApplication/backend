package com.movie.movieapi.service;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.ReviewInsertRequestDto;
import com.movie.movieapi.dto.ReviewSelectResponseDto;
import com.movie.movieapi.dto.ReviewUpdateRequestDto;
import com.movie.movieapi.repository.ReviewRepository;
import com.movie.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    @Transactional
    //@CacheEvict(value = "review", allEntries = true)
    public void insertReview(ReviewInsertRequestDto reviewInsertRequestDto, User user) {
        User userInfo = userRepository.findByKakaoId(user.getKakaoId())
                .orElseThrow(null);

        boolean review = reviewRepository.existsByUserAndContentAndDelYn(userInfo,reviewInsertRequestDto.getContent(),false);

        if(review){
            throw new NotFoundException("같은 내용으로 리뷰를 작성할 수 없습니다.");
        }
        reviewRepository.save(new Review(reviewInsertRequestDto, userInfo));
    }

    @Transactional(readOnly = true)
    //@Cacheable(value = "review")
    public Page<ReviewSelectResponseDto> selectReviews(Long movieId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByMovieId(movieId,pageable);
        return reviews.map(review -> ReviewSelectResponseDto.builder()
                .reviewId(review.get_id())
                .kakaoId(review.getUser().getKakaoId())
                .userNickname(review.getUser().getUserNickname())
                .content(review.getContent())
                .delYn(review.isDelYn())
                .build());

    }

    //@CacheEvict(value = "review", allEntries = true)
    @Transactional
    public void updateReview(ReviewUpdateRequestDto requestDto,User user) {
        Review review = reviewRepository.findById(requestDto.getReviewId())
                .orElseThrow(null);
        //본인 글인지 판단
        if(review.getUser().getKakaoId().equals(user.getKakaoId())){
            //리뷰 수정
            review.updateReview(requestDto);
            //DB 저장
            reviewRepository.save(review);
        }else{
            throw new NotFoundException("수정 할수 없습니다.");
        }
    }


    //@CacheEvict(value = "review", allEntries = true)
    @Transactional
    public void deleteReview(String reviewId, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(null);
        //본인 글인지 판단
        if(review.getUser().getKakaoId().equals(user.getKakaoId())){
            //리뷰 삭제
            review.deleteReview();
            //DB 저장
            reviewRepository.save(review);
        }else{
            throw new NotFoundException("삭제 할수 없습니다.");
        }
    }
}
