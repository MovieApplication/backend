package com.movie.movieapi.service;

import com.movie.movieapi.domain.RefreshToken;
import com.movie.movieapi.domain.User;
import com.movie.movieapi.dto.TokenRequestDto;
import com.movie.movieapi.dto.UserInsertRequestDto;
import com.movie.movieapi.dto.UserLoginResponseDto;
import com.movie.movieapi.exception.CommonErrorCode;
import com.movie.movieapi.exception.RestApiException;
import com.movie.movieapi.exception.UserErrorCode;
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
        //기존 동일 회원 체크
        boolean userCheck = userRepository.existsByUserNicknameAndKakaoId(userInsertRequestDto.getUserNickname(), userInsertRequestDto.getKakaoId());
        if (userCheck) {
            throw new RestApiException(UserErrorCode.EXIST_USER);
        }
        //DB 저장
        userRepository.save(new User(userInsertRequestDto));
    }

    public UserLoginResponseDto login(String kakaoId) {
        User userInfo = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.INACTIVE_USER));

        //access token 생성
        final String accessToken = jwtUtil.generateToken(userInfo);
        //refresh token 생성
        final String refreshToken = jwtUtil.generateRefreshToken(userInfo);

        RefreshToken refreshTokenInfo = refreshTokenRepository.findByUser(userInfo)
                .orElse(null);
        if (refreshTokenInfo != null) {
            //처음 로그인이 아닌 사람.
            refreshTokenInfo.setRefreshToken(refreshToken, userInfo);
            return new UserLoginResponseDto(accessToken, refreshTokenRepository.save(refreshTokenInfo).get_id());
        } else {
            //처음 로그인한 사람.
            RefreshToken newRefreshToken = new RefreshToken();
            newRefreshToken.setRefreshToken(refreshToken, userInfo);
            return new UserLoginResponseDto(accessToken, refreshTokenRepository.save(newRefreshToken).get_id());
        }
    }

    public boolean userInfoCheck(String kakaoId) {
        return userRepository.existsByKakaoId(kakaoId);
    }

    public UserLoginResponseDto refreshToken(TokenRequestDto tokenRequestDto) {
        RefreshToken refreshTokenInfo = refreshTokenRepository.findBy_id(tokenRequestDto.getRefreshTokenId())
                .orElseThrow(()-> new RestApiException(CommonErrorCode.NOT_FOUND_REFRESH_TOKEN));

        //리프레시 토큰이 유효할 경우.
        if(jwtUtil.validateRefreshToken(refreshTokenInfo.getRefreshToken(),refreshTokenInfo.getUser().getKakaoId())){
            String accessToken = jwtUtil.generateToken(refreshTokenInfo.getUser().getKakaoId());
            return new UserLoginResponseDto(accessToken,refreshTokenInfo.get_id());
        }
        return null;
    }
}
