package com.movie.movieapi.service;

import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.UserInsertRequestDto;
import com.movie.movieapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void insertUserInfo(UserInsertRequestDto userInsertRequestDto) {
        userRepository.save(new User(userInsertRequestDto));
    }
}
