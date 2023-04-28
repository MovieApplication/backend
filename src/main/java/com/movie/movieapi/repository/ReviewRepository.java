package com.movie.movieapi.repository;

import com.movie.movieapi.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review,String>{
    List<Review> findAllByMovieId(String movieId);
}
