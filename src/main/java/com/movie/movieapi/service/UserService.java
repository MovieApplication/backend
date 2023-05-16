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
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public void insertUserInfo(UserInsertRequestDto userInsertRequestDto) {
        //기존 동일 회원 체크
        boolean userCheck = userRepository.existsByUserNicknameAndKakaoId(userInsertRequestDto.getUserNickname(),userInsertRequestDto.getKakaoId());
        if(userCheck){
            throw new NotFoundException("이미 해당 정보로 등록된 회원이 있습니다.");
        }
        //DB 저장
        userRepository.save(new User(userInsertRequestDto));
    }

    public UserLoginResponseDto login(String kakaoId) {
        User userInfo = userRepository.findByKakaoId(kakaoId).orElseThrow(null);

        //access token 생성
        final String accessToken = jwtUtil.generateToken(userInfo);
        //refresh token 생성
        final String refreshToken = jwtUtil.generateRefreshToken(kakaoId);

        RefreshToken refreshTokenInfo = new RefreshToken();
        refreshTokenInfo.setRefreshToken(refreshToken,userInfo);
        return new UserLoginResponseDto(accessToken,refreshTokenRepository.save(refreshTokenInfo).get_id());
    }

    public boolean userInfoCheck(String kakaoId) {
       return userRepository.existsByKakaoId(kakaoId);
    }
}
