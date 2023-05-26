package com.movie.movieapi.repository;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReviewRepository extends MongoRepository<Review,String>{

    boolean existsByUserAndMovieIdAndContentAndDelYn(User userInfo, Long movieId, String content, boolean delYn);


    Page<Review> findAllByMovieIdAndDelYn(Long movieId, boolean delYn, Pageable pageable);
}
