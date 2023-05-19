package com.movie.movieapi.repository;

import com.movie.movieapi.domain.Review;
import com.movie.movieapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReviewRepository extends MongoRepository<Review,String>{
    Page<Review> findAllByMovieId(Long movieId, Pageable pageable);
    boolean existsByUserAndContentAndDelYn(User userInfo, String content, boolean delYn);
}
