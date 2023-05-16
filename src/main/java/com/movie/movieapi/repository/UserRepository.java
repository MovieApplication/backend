package com.movie.movieapi.repository;

import com.movie.movieapi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUserNickname(String userId);

    boolean existsByUserNicknameAndKakaoId(String userNickname, String kakaoId);

    Optional<User> findByKakaoId(String kakaoId);

    boolean existsByKakaoId(String kakaoId);
}
