package com.movie.movieapi.service;

import com.movie.movieapi.domain.RefreshToken;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.UserInsertRequestDto;
import com.movie.movieapi.dto.UserLoginResponseDto;
import com.movie.movieapi.repository.RefreshTokenRepository;
import com.movie.movieapi.repository.UserRepository;
import com.movie.movieapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public void insertUserInfo(UserInsertRequestDto userInsertRequestDto) {
        userRepository.save(new User(userInsertRequestDto));
    }

    public UserLoginResponseDto login(String userId) {
        User userInfo = userRepository.findByUserId(userId).orElseThrow(null);

        //access token 생성
        final String accessToken = jwtUtil.generateToken(userInfo);
        //refresh token 생성
        final String refreshToken = jwtUtil.generateRefreshToken(userId);

        RefreshToken refreshTokenInfo = new RefreshToken();
        refreshTokenInfo.setRefreshToken(refreshToken,userInfo);
        return new UserLoginResponseDto(accessToken,refreshTokenRepository.save(refreshTokenInfo).get_id());
    }
}
