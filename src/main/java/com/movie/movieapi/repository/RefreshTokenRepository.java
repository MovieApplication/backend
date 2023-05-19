package com.movie.movieapi.repository;

import com.movie.movieapi.domain.RefreshToken;
import com.movie.movieapi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findBy_idAndUser(String refreshTokenId, User user);
}
