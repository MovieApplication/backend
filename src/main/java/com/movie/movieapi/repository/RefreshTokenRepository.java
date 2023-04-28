package com.movie.movieapi.repository;

import com.movie.movieapi.domain.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    RefreshToken findByRefreshToken(String refreshToken);
}
