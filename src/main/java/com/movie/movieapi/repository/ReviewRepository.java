package com.movie.movieapi.repository;

import com.movie.movieapi.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review,String>{
    Page<Review> findAllByMovieId(String movieId, Pageable pageable);
}
